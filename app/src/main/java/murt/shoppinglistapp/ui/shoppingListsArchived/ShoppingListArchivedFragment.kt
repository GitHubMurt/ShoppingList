package murt.shoppinglistapp.ui.shoppingListsArchived


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_shopping_list_archived.*
import murt.data.model.ShoppingList

import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.BaseFragment
import murt.shoppinglistapp.ui.shoppingListDetails.ShoppingListDetailsActivity
import murt.shoppinglistapp.ui.shoppingListsCurrent.ListOfShoppingListsAdapter
import org.kodein.di.generic.instance
import javax.inject.Inject




/**
 * A simple [Fragment] subclass.
 */
class ShoppingListArchivedFragment : BaseFragment() {


    companion object {
        fun newInstance() = ShoppingListArchivedFragment()
    }

    private val viewModelFactory: ShoppingListArchivedViewModelFactory by instance(ShoppingListArchivedViewModelFactory::class.java.simpleName)
    private lateinit var mViewModel: ShoppingListArchivedViewModel

    private val mAdapter by lazy {
        ListOfShoppingListsAdapter(this::onShoppingListClick, this::onDeArchiveShoppingList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list_archived, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        setUpViewModel()

    }

    private fun setUpView(){
        srf_list_archived.setOnRefreshListener {
            mViewModel.refreshList()
        }

        rv_list_archived.adapter = mAdapter
    }

    private fun setUpViewModel(){
        mViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ShoppingListArchivedViewModel::class.java)

        mViewModel.getArchivedShoppingList().observe(viewLifecycleOwner, Observer {
            srf_list_archived.isRefreshing = false

            if(it == null) return@Observer

            mAdapter.updateList(it)
        })
    }

    private fun onShoppingListClick(shoppingList: ShoppingList){
        ShoppingListDetailsActivity.openShoppingListDetails(context!!, shoppingList.id!!, false)
    }

    private fun onDeArchiveShoppingList(shoppingList: ShoppingList){
        mViewModel.moveToCurrent(shoppingList)
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onMessageEvent(event: ShoppingListArchived) {
//        mViewModel.refreshList()
//    }

}// Required empty public constructor
