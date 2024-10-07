package com.example.cardingo.ui.fragment.learned

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cardingo.R
import com.example.cardingo.data.entity.Words
import com.example.cardingo.databinding.FragmentLearnedBinding
import com.example.cardingo.ui.adapter.LearnedAdapter
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson


class LearnedFragment : Fragment() {
    private lateinit var binding: FragmentLearnedBinding
    private lateinit var learnedAdapter: LearnedAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var savedWordsList: MutableList<Words>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLearnedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences =
            requireContext().getSharedPreferences("LearnedWord", Context.MODE_PRIVATE)

        loadSavedWords()

        val recyclerView = binding.rvLearned
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        learnedAdapter = LearnedAdapter(savedWordsList,sharedPreferences)
        recyclerView.adapter = learnedAdapter

        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val item = savedWordsList[position]
                    removeItem(position)
                    removeWordFromSharedPreferences(item)
                    learnedAdapter.notifyItemChanged(position)
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    val icon = ContextCompat.getDrawable(recyclerView.context, R.drawable.baseline_delete_24)
                    val iconMargin = (viewHolder.itemView.height - icon!!.intrinsicHeight) / 2

                    if (dX > 0) {
                        val itemView = viewHolder.itemView
                        val iconTop = itemView.top + iconMargin
                        val iconBottom = iconTop + icon.intrinsicHeight
                        val iconLeft = itemView.left + iconMargin
                        val iconRight = iconLeft + icon.intrinsicWidth

                        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        icon.draw(c)


                        val background = ColorDrawable(Color.RED)
                        background.setBounds(
                            itemView.left,
                            itemView.top,
                            itemView.left + dX.toInt(),
                            itemView.bottom
                        )
                        background.draw(c)
                    }

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.let {
//                    filterWordsByTab(it.text.toString())
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {}
//            override fun onTabReselected(tab: TabLayout.Tab?) {}
//
//        })
//        // Tab başlıklarını ekle
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Tümü"))
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("English"))
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Español"))
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Deutsch"))
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Français"))
    }
//    // Tab'a göre filtreleme işlemi
//    private fun filterWordsByTab(language: String) {
//        val filteredWords = if (language == "Tümü") {
//            savedWordsList
//        } else {
//            savedWordsList.filter { it.language == language }
//        }
//
//        // Adapter'ı güncelle
//        learnedAdapter.updateList(filteredWords.toMutableList())
//    }

    private fun loadSavedWords() {
        val gson = Gson()
        val savedWordsSet =
            sharedPreferences.getStringSet("savedWords", mutableSetOf()) ?: mutableSetOf()

        if (savedWordsSet.isNotEmpty()) {
            savedWordsList = savedWordsSet.map { json ->
                gson.fromJson(json, Words::class.java)
            }.toMutableList()
        } else {
            savedWordsList = mutableListOf()
        }
    }

    private fun removeItem(position: Int) {
        if (position in savedWordsList.indices) {
            savedWordsList.removeAt(position)
            learnedAdapter.notifyItemRemoved(position)
        }
    }

    private fun removeWordFromSharedPreferences(word: Words) {
        val gson = Gson()
        val json = gson.toJson(word)

        val savedWordsSet =
            sharedPreferences.getStringSet("savedWords", mutableSetOf())?.toMutableSet()
                ?: mutableSetOf()
        savedWordsSet.remove(json)

        val editor = sharedPreferences.edit()
        editor.putStringSet("savedWords", savedWordsSet).apply()

    }


}