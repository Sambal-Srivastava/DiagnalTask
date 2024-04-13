package com.diagnal.diagnaltask.views

import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.diagnal.diagnaltask.R
import com.diagnal.diagnaltask.base.BaseActivity
import com.diagnal.diagnaltask.data.model.Content
import com.diagnal.diagnaltask.data.network.Resource
import com.diagnal.diagnaltask.databinding.ActivityMainBinding
import com.diagnal.diagnaltask.views.adapters.ContentAdapter
import com.diagnal.diagnaltask.utils.GridSpaceLayoutmanager
import com.diagnal.diagnaltask.utils.hideKeyboard
import com.diagnal.diagnaltask.utils.showKeyboard
import com.diagnal.diagnaltask.utils.toast
import com.diagnal.diagnaltask.viewmodel.CommonViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: CommonViewModel by viewModels()
    private val mContentAdapter = ContentAdapter(ArrayList<Content>(), this) {

    }

    private var page = 1

    override fun setUpViews() {

        lifecycle.addObserver(viewModel)

        val columns = resources.getInteger(R.integer.gallery_columns)
        binding.rvContent.apply {
            layoutManager = GridLayoutManager(this@MainActivity, columns)
            addItemDecoration(GridSpaceLayoutmanager(columns, 40, true))
            adapter = mContentAdapter

            addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager: GridLayoutManager =
                        recyclerView.getLayoutManager() as GridLayoutManager
                    val visibleItemCount: Int = layoutManager.getChildCount()
                    val totalItemCount: Int = layoutManager.getItemCount()
                    val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                    if (totalItemCount - visibleItemCount <= firstVisibleItemPosition + 9) {
                        // Load more data
                        page++
                        if (page <= 3) {
                            viewModel.callApiLogin(page.toString())
                        }
                    }
                }
            })
        }

        viewModel.loginMyResponse.observe(this) {
            when (it.status) {
                Resource.Status.LOADING -> {

                }

                Resource.Status.SUCCESS -> {
                    mContentAdapter.updateList(
                        it.data?.pageDto?.contentDto?.content!! as ArrayList<Content>,
                        false, true
                    )
                }

                Resource.Status.ERROR -> {

                }
            }
        }

        //======handle search==========
        binding.ivSearch.setOnClickListener() {
            if (binding.etSearch.visibility == View.GONE) {
                binding.ivBack.visibility = View.GONE
                binding.etSearch.visibility = View.VISIBLE
                binding.tvTitle.visibility = View.GONE
                binding.ivSearch.setImageResource(R.drawable.search_cancel)
                binding.ivSearch.showKeyboard(binding.etSearch, true)
            } else {
                binding.ivBack.visibility = View.VISIBLE
                binding.tvTitle.visibility = View.VISIBLE
                binding.etSearch.visibility = View.GONE
                binding.ivSearch.setImageResource(R.drawable.search)
                binding.etSearch.text = null
                binding.ivSearch.hideKeyboard(this@MainActivity)
            }
        }

        // =======Handle back button click========
        binding.ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPressed()
            }
        })
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!! == "") {
                    mContentAdapter.updateListWithMain()
                } else if (s.length >= 3) {
                    mContentAdapter.filter(s.toString())
                } else {
                    mContentAdapter.updateListWithMain()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun handleBackPressed() {
        if (viewModel.backPressedTime + 2000 > System.currentTimeMillis()) {
            finish()
        } else {
            this.toast(getString(R.string.back_button_exit_app_msg))
        }
        viewModel.backPressedTime = System.currentTimeMillis()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflator = menuInflater
        inflator.inflate(R.menu.searchmenu, menu)

        val searchViewItem: MenuItem = menu!!.findItem(R.id.search_bar)
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mContentAdapter.filter(query.toString())
                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mContentAdapter.filter(newText.toString());
                return false;
            }

        })
        return super.onCreateOptionsMenu(menu)
    }


}