package com.example.wheatherforcast.favourites.view


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheatherforcast.R
import com.example.wheatherforcast.databinding.FragmentFavouriteScreenBinding
import com.example.wheatherforcast.db.favouritedb.FavLocalSource
import com.example.wheatherforcast.favourites.model.FavModel
import com.example.wheatherforcast.favourites.model.FavouriteRepository
import com.example.wheatherforcast.favourites.viewmodel.FavouriteViewModel
import com.example.wheatherforcast.favourites.viewmodel.FavouriteViewModelfactory
import com.example.wheatherforcast.utils.AppDialogs
import com.example.wheatherforcast.utils.Constants
import com.example.wheatherforcast.utils.NetworkConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavouriteScreen : Fragment(), onFavouriteClickListener {

    lateinit var binding: FragmentFavouriteScreenBinding
    lateinit var factory: FavouriteViewModelfactory
    lateinit var viewModel: FavouriteViewModel
    lateinit var favAdapter: FavouriteAdapter
    lateinit var dialog: Dialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavouriteScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = FavouriteViewModelfactory(
            FavouriteRepository.getInstance(FavLocalSource(requireContext())), requireContext()
        )
        viewModel = ViewModelProvider(this, factory)[FavouriteViewModel::class.java]

        setaddFavBtn(view)
        getData()

    }

    fun setaddFavBtn(view: View) {
        binding.addFavFab.setOnClickListener {
            if (NetworkConnection.isOnline(requireContext())==true) {
                Constants.fromfav=true
                Navigation.findNavController(view)
                    .navigate(R.id.action_favouriteScreen_to_mapFragment)
            }
            else{
                Toast.makeText(requireContext(),"You have to connect network",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getData() {
        favAdapter = FavouriteAdapter(this, requireContext())
        lifecycleScope.launch() {
            var list = viewModel._mutableList?.collect {

                binding.favList?.apply {
                    adapter = favAdapter
                    layoutManager = LinearLayoutManager(this.context).apply {
                        orientation = RecyclerView.VERTICAL
                        if (it?.isEmpty() == false) {
                            binding.favList.visibility = View.VISIBLE
                            favAdapter.submitList(it)
                            Log.i("Submit", it.size.toString())
                        } else {
                            binding.favList.visibility = View.GONE
                            binding.favEmptyImg.visibility = View.VISIBLE
                        }

                    }
                }
            }
        }


    }

    override fun goToLcation(favLocation: FavModel) {

        Constants.favModel = true
        Constants.model = favLocation
        val action: NavDirections =
            FavouriteScreenDirections.actionFavouriteScreenToHomeFragment23()
        if (NetworkConnection.isOnline(requireContext())==true){
        Navigation.findNavController(requireView())
            .navigate(action)}
        else
            Toast.makeText(requireContext(),"You have to connect network",Toast.LENGTH_SHORT).show()

    }

    override fun deletelocation(favLocation: FavModel) {
        setDeleteConfirmationDialog(favLocation)
    }

    fun setDeleteConfirmationDialog(favLocation: FavModel) {
        dialog = AppDialogs.setDialog(
            "Do you want to delete this location?",
            requireContext(),
            R.layout.dialog_layout
        )
        val cancleBtn = dialog.findViewById(R.id.fav_di_cancle) as Button
        val deleteBtn = dialog.findViewById(R.id.fav_di_save) as Button
        deleteBtn.setText(R.string.delete)

        deleteBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.deleteLocation(favLocation)


            }
            dialog.dismiss()
        }
        cancleBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}





