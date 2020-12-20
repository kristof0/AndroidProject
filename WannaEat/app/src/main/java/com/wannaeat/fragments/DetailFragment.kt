package com.wannaeat.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.wannaeat.Injection
import com.wannaeat.R
import com.wannaeat.model.Repo
import com.wannaeat.ui.SearchRepositoriesViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private lateinit var repo: Repo
    private lateinit var viewModel: SearchRepositoriesViewModel
    private lateinit var name: TextView
    private lateinit var address: TextView
    private lateinit var price: TextView
    private lateinit var phone: TextView
    private lateinit var web: TextView
    private lateinit var image: ImageView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_detail, container, false)

        viewModel = ViewModelProviders.of(this, this.context?.let { Injection.provideViewModelFactory(it) })
                .get(SearchRepositoriesViewModel::class.java)



        return view
    }

    init {

    }

    override fun onStart() {
        super.onStart()

        repo = SearchRepositoriesViewModel.selectedRepo!!
        name = requireView().findViewById(R.id.detail_name)
        address = requireView().findViewById(R.id.detail_address)
        price = requireView().findViewById(R.id.detail_price)
        phone = requireView().findViewById(R.id.detail_phone)
        image = requireView().findViewById(R.id.detail_imageView)
        web = requireView().findViewById(R.id.detail_web)
        name.text = repo.name
        address.text = repo.address
        price.text = resources.getString(R.string.price, repo.price.toString())
        phone.text = resources.getString(R.string.phone, repo.phone)
        web.text = resources.getString(R.string.web, repo.mobile_reserve_url)
        //web.text=repo.mobile_reserve_url
        web.setOnClickListener() {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.mobile_reserve_url))
            requireView().context.startActivity(intent)
        }


        Glide.with(this.image)
                .load(repo.image_url)
                .into(image)

        val fm: FragmentManager = childFragmentManager
        val mMapFragment: MapFragment = MapFragment.newInstance(repo.lat, repo.lng)
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.add(R.id.detail_mapFrag, mMapFragment)
        fragmentTransaction.commit()
        fm.executePendingTransactions()


    }


}