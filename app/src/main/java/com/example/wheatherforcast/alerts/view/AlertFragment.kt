package com.example.wheatherforcast.alerts.view


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.wheatherforcast.R
import com.example.wheatherforcast.alerts.model.AlertModel
import com.example.wheatherforcast.alerts.model.AlertRepository
import com.example.wheatherforcast.alerts.model.DateAlertModel
import com.example.wheatherforcast.alerts.viewmodel.AlertViewModel
import com.example.wheatherforcast.alerts.viewmodel.AlertViewModelFactory
import com.example.wheatherforcast.alerts.worker.AlertWorker
import com.example.wheatherforcast.databinding.AddAlertBinding
import com.example.wheatherforcast.databinding.FragmentAlertsScreenBinding
import com.example.wheatherforcast.db.alertdb.AlertLocalSource
import com.example.wheatherforcast.utils.AppDialogs
import com.example.wheatherforcast.utils.Constants
import com.example.wheatherforcast.utils.LanguageConverter
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AlertFragment : Fragment(), onAlertClickListener {

    var yearStart = 0
    var monthStart = 0
    var dayStart = 0
    var hourStart = 0
    var minutsStart = 0

    var yearEnd = 0
    var monthEnd = 0
    var dayEnd = 0
    var hourEnd = 0
    var minutsEnd = 0
    var type = "Alarm"

    lateinit var timeStart: EditText
    lateinit var timeEnd: EditText
    lateinit var dateStart: EditText
    lateinit var dateEnd: EditText

    lateinit var binding: FragmentAlertsScreenBinding
    lateinit var bottomSheet: AddAlertBinding
    lateinit var factory: AlertViewModelFactory
    lateinit var viewModel: AlertViewModel
    lateinit var alertAdapter: AlertAdapter
    lateinit var sharedPrefrences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefrences =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor = sharedPrefrences.edit()
        LanguageConverter.checkLanguage(
            sharedPrefrences.getString(Constants.language, "")!!,
            requireContext()
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlertsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = AlertViewModelFactory(
            AlertRepository.getInstance(AlertLocalSource(requireContext()))
        )
        viewModel = ViewModelProvider(this, factory)[AlertViewModel::class.java]
        val pm = requireView().context.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!Settings.canDrawOverlays(requireView().context)) {
            askForDrawOverlaysPermission()
        }
        getData()
        showBottomSheet(view)
    }


    fun addAlertBottomSheet(v: View): BottomSheetDialog {
        var dialog = BottomSheetDialog(this.requireContext(), R.style.SheetDialog)
        val view = layoutInflater.inflate(R.layout.add_alert, null)
        bottomSheet = AddAlertBinding.inflate(layoutInflater)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bottomSheet.btnCancleAlert.setOnClickListener {

            dialog.dismiss()
        }
        timeStart = bottomSheet.edtStartTime
        dateStart = bottomSheet.edtStartDate

        timeEnd = bottomSheet.edtEndTime
        dateEnd = bottomSheet.edtEndDate

        timeStart.setOnClickListener() {
            createDatePickerDialog("From")
        }
        timeEnd.setOnClickListener() {
            createDatePickerDialog("TO")

        }

        bottomSheet.btnAddAlert.setOnClickListener {
            var dateAlerModelStart =
                DateAlertModel(yearStart, monthStart, dayStart, hourStart, minutsStart)
            var dateAlerModelEnd = DateAlertModel(yearEnd, monthEnd, dayEnd, hourEnd, minutsEnd)
            if (bottomSheet.BtnNotification.isChecked() == true) {
                type = "Notification"
            }
            Log.i("after checking", type)
            alertNotificationOrAlarm(dateAlerModelStart, dateAlerModelEnd, type)
            dialog.dismiss()

        }

        dialog.setCancelable(false)
        dialog.dismissWithAnimation = true
        dialog.setContentView(bottomSheet.root)

        return dialog
    }

    fun getData() {
        alertAdapter = AlertAdapter(this, requireContext())
        lifecycleScope.launch() {
            var list = viewModel._mutableList?.collect {

                binding.alertsList?.apply {
                    adapter = alertAdapter
                    layoutManager = LinearLayoutManager(this.context).apply {
                        orientation = RecyclerView.VERTICAL
                        if (it?.isEmpty() == false) {
                            binding.alertsList.visibility = View.VISIBLE
                            binding.noAlertsImg.visibility = View.GONE
                            alertAdapter.submitList(it)
                            Log.i("Submited alerts", it.size.toString())
                        } else {
                            binding.alertsList.visibility = View.GONE
                            binding.noAlertsImg.visibility = View.VISIBLE
                        }

                    }
                }
            }
        }


    }

    fun showBottomSheet(v: View) {

        binding.addAlert.setOnClickListener {
            addAlertBottomSheet(v).show()
        }
    }

    override fun deleteAlert(alert: AlertModel) {
        setDeleteConfirmationDialog(alert)
    }


    fun setDeleteConfirmationDialog(alert: AlertModel) {
        dialog = AppDialogs.setDialog(
            "Do you want to delete this alert?",
            requireContext(),
            R.layout.dialog_layout
        )
        val cancleBtn = dialog.findViewById(R.id.fav_di_cancle) as Button
        val deleteBtn = dialog.findViewById(R.id.fav_di_save) as Button
        deleteBtn.setText(R.string.delete)

        deleteBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.deleteAlert(alert)


            }
            dialog.dismiss()
        }
        cancleBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askForDrawOverlaysPermission() {
        if (!Settings.canDrawOverlays(requireView().context)) {
            if ("xiaomi" == Build.MANUFACTURER.lowercase(Locale.ROOT)) {
                val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
                intent.setClassName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.permissions.PermissionsEditorActivity"
                )
                intent.putExtra("extra_pkgname", requireView().context.packageName)
                AlertDialog.Builder(requireView().context)
                    .setTitle("draw_overlays")
                    .setMessage("draw_overlays_description")
                    .setPositiveButton("go_to_settings") { dialog, which ->
                        startActivity(intent)
                    }
                    .show()
            } else {
                AlertDialog.Builder(requireView().context)
                    .setTitle(getString(R.string.Warning))
                    .setMessage(getString(R.string.Permission_Required))
                    .setPositiveButton(getString(R.string.Ok)) { _, _ ->
                        val permissionIntent = Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + requireView().context.packageName)
                        )
                        runtimePermissionResultLauncher.launch(permissionIntent)
                    }
                    .show()
            }
        }

    }

    private val runtimePermissionResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    fun alertNotificationOrAlarm(start: DateAlertModel, end: DateAlertModel, type: String) {
        if (start != null && end != null) {

            var startDate = Calendar.getInstance()
            startDate.set(start.year, start.month, start.day, start.hour, start.minute)

            var endDate = Calendar.getInstance()
            endDate.set(end.year, end.month, end.day, end.hour, end.minute)

            var duration = (endDate.timeInMillis / 1000L) - (startDate.timeInMillis / 1000L)
            val inputData = Data.Builder()
                .putString("title", "Weather")
                .putString("content", "current weather statue")
                .putString("typeAlert", type)
                .build()

            val fireAlertConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<AlertWorker>()
                .setInitialDelay(duration, TimeUnit.SECONDS)
                .setInputData(inputData)
                .setConstraints(fireAlertConstraints)
                .build()

            var alertModel = AlertModel(
                (oneTimeWorkRequest.id).toString(),
                timeStart.text.toString(),
                timeEnd.text.toString(),
                dateStart.text.toString(),
                dateEnd.text.toString()
            )
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.insertNewAlert(alertModel)
            }
            WorkManager.getInstance(requireContext().applicationContext).enqueue(oneTimeWorkRequest)


        }
        //   WorkManager.getInstance().cancelWorkById(oneTimeWorkRequest.id)

    }

    fun createDatePickerDialog(duration: String) {

        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, day: Int ->
                val c = Calendar.getInstance()
                c[Calendar.YEAR] = year
                c[Calendar.MONTH] = month
                c[Calendar.DAY_OF_MONTH] = day
                val form = SimpleDateFormat(" d /MM/yyyy")
                var date = form.format(c.time)

                if (duration.equals("From")) {
                    this.yearStart = year
                    this.monthStart = month
                    this.dayStart = day
                    dateStart.setText(date)

                } else {
                    this.yearEnd = year
                    this.monthEnd = month
                    this.dayEnd = day
                    dateEnd.setText(date)

                }


                createTimePickerDialog(duration)

            },
            year,
            month,
            day
        ).show()
    }

    fun createTimePickerDialog(duration: String) {
        val calendar = Calendar.getInstance()
        val hours = calendar[Calendar.HOUR_OF_DAY]
        val mints = calendar[Calendar.MINUTE]
        TimePickerDialog(
            activity,
            TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hour: Int, minuts: Int ->
                val c = Calendar.getInstance()
                c[Calendar.HOUR_OF_DAY] = hour
                c[Calendar.MINUTE] = minuts
                c.timeZone = TimeZone.getDefault()
                val form = SimpleDateFormat("KK:mm a")
                var time = form.format(c.time)

                if (duration.equals("From")) {
                    this.hourStart = hour
                    this.minutsStart = minuts
                    timeStart.setText(time)
                } else {
                    this.hourEnd = hour
                    this.minutsEnd = minuts
                    timeEnd.setText(time)
                }

            },
            hours,
            mints,
            DateFormat.is24HourFormat(requireContext())
        ).show()

    }
}
