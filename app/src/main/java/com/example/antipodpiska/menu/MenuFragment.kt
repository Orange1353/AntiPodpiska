package com.example.antipodpiska.menu

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.AdapterView
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.Align
import com.example.antipodpiska.R
import com.example.antipodpiska.addition.AddSubActivityFragments
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.example.antipodpiska.subList.SubAdapter
import com.example.antipodpiska.subList.SubListViewModel
import kotlinx.android.synthetic.main.delete_delete.*


const val SUB_ID = "sub id"
const val SUB_NAME = "name"
const val SUB_DESCRIPTION = "description"
const val TYPE = "typeSub"
const val CARD = "card"
const val DATE_PAY = "pay"
const val FREE_PERIOD = "freePeriod"
const val COST = "cost"
const val PERIOD = "Period"
const val TYPE_FREE = "typeFreePeriod"
const val CURR_COST = "typeCost"
const val TYPE_PERIOD = "typePeriod"
const val PUSH = "push"


class MenuFragment : Fragment(){

    private val newSubActivityRequestCode = 1
    private val TAG = javaClass.simpleName
    private var heightCollapsedItem = 0
    private var heightExpandedItem = 0
    private var itemToResize = 0
    private var init = false
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var dyAbs = 0
    private val adapter = SubAdapter({ sub -> adapterOnClick(sub) })

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var communicator: CommunicatorMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view: View  = inflater.inflate(R.layout.fragment_menu, container, false)
        val context: Context? = getContext()

        val subsAdapter = SubAdapter({ sub -> adapterOnClick(sub) })
        //      val concatAdapter = ConcatAdapter(headerAdapter, subsAdapter)

       recyclerView= view!!.findViewById(R.id.recycler_view_menu)

       val linearLayoutManager = LinearLayoutManager(context)

//        recyclerView.layoutManager = linearLayoutManager
        var countSub = 0


        val button_archive: Button = view.findViewById(R.id.btn_archive)

        button_archive.text = "Архив"

        communicator = activity as CommunicatorMenu




        button_archive.setOnClickListener {

            communicator.replaceFragment(ArchiveFragment())


        }










    //    recyclerView.setHasFixedSize(true)


      /*  recyclerView.addOnScrollListener( FocusResizeScrollListener(
                subsAdapter,
                linearLayoutManager
            )
        )*/

        /* FocusResizeScrollListener(
                subsAdapter,
                linearLayoutManager
            )*/


    //         recyclerView.addItemDecoration(ItemDecorator(-90))
    //  recyclerView.addItemDecoration(OverlapDecoration())
    //    recyclerView.addItemDecoration(SimpleDividerItemDecorationLastExcluded(100))


        recyclerView.adapter = subsAdapter
        var subsListViewModel = ViewModelProvider(activity!!).get(SubListViewModel::class.java)

       // (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.my_toolbar))

        var searchView: SearchView = view.findViewById(R.id.search_view)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {

                if (s != "") {
                    var list1: List<Sub> = ArrayList<Sub>()
                    for (i in subsListViewModel.subsLiveData.value!!.indices) {
                        if (subsListViewModel.subsLiveData.value!![i].name.toLowerCase().contains(
                                s.toLowerCase(),
                                false
                            ) && subsListViewModel.subsLiveData.value!![i].status == "Активна"
                        )
                            list1 = list1.plus(subsListViewModel.subsLiveData.value!![i])
                    }
                    subsAdapter.submitList(list1)
                } else {
                    var list1: List<Sub> = ArrayList<Sub>()

                    for (i in subsListViewModel.subsLiveData.value!!.indices) {
                        if (subsListViewModel.subsLiveData.value!![i].status == "Активна")
                            list1 = list1.plus(subsListViewModel.subsLiveData.value!![i])
                    }

                    subsAdapter.submitList(list1)
                }


                // Toast.makeText(context, "0" + s + "0", Toast.LENGTH_SHORT).show()
                return true
            }
        })

  //      recyclerView.addItemDecoration(MarginItemDecoration( 40))

        subsListViewModel.subsLiveData.observe(this) {
            it?.let {
countSub = it.indices.last
                var list1:List<Sub> = ArrayList<Sub>()
                for (i in it.indices) {
                    if(it[i].status == "Активна")
                    list1 = list1.plus(it[i])
                }

                subsAdapter.submitList(list1)
           //     subsAdapter.submitList(it as MutableList<Sub>)
                //              headerAdapter.updateSubCount(it.size)
            }

        }

      //  recyclerView.setNestedScrollingEnabled(false) //Close nested slide

   //     recyclerView.layoutManager = StackCardLayoutManager(6)



//оставить хоя бы одну для нижней границы
     /*
            /*   recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView,
                state: RecyclerView.State
            ) {
                   if (parent.getChildAdapterPosition(view) == parent.getAdapter()!!.getItemCount() - 1) {
                    outRect.top =-30 //parent.layoutManager.height
                }

          /*      if (parent.getChildAdapterPosition(view) == (parent.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()) {
                    outRect.top =-125/parent.getAdapter()!!.getItemCount() //parent.layoutManager.height
                }*/
                  /* if (parent.getChildAdapterPosition(view) == (parent.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()-1) {
                     outRect.top =(-205) //parent.layoutManager.height
                } */
            }
        })*/


            //не захватывает движение вверх
            /*  recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.e("7777777777", "7777777777")
                recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect, view: View, parent: RecyclerView,
                        state: RecyclerView.State
                    ) {

                        if (recyclerView.getScrollY() > 0) {

                            Log.e("----------------", outRect.top.toString())
                            outRect.top =
                                outRect.top - 10 //-125/parent.getAdapter()!!.getItemCount() //parent.layoutManager.height
                            Log.e("----------------", outRect.top.toString())
                            recyclerView.post {
                                adapter.notifyDataSetChanged()
                            }


                        } /*else {
                    Log.e("++++++++++++++++", outRect.top.toString())
                    outRect.top = outRect.top + 10
                    Log.e("++++++++++++++++", outRect.top.toString())
                    recyclerView.post {
                        adapter.notifyDataSetChanged()
                    }
                }*/
                    }
                })
                /* try {
                    linearLayoutManager!!.findFirstCompletelyVisibleItemPosition()
    //    recyclerView.getChildViewHolder(view).itemView.offsetTopAndBottom(50)
                    for (j in 0 until linearLayoutManager.itemCount - 1) {
                        val view = recyclerView.getChildAt(j)
                        if (view != null) {
                         /*   if (recyclerView.getChildViewHolder(view).layoutPosition == linearLayoutManager.findLastVisibleItemPosition() - 1) {
                                recyclerView.getChildViewHolder(view).itemView.setPadding(
                                    0,
                                    0,
                                    0,
                                    100
                                )*/
                                //      recyclerView.getChildViewHolder(view).itemView.offsetTopAndBottom(100)
                                view.layoutParams.height = heightCollapsedItem//!!!!!!!!!!
                                val buttonLayoutParams: RecyclerView.LayoutParams =
                                    view.layoutParams as RecyclerView.LayoutParams
                                buttonLayoutParams.marginStart = 30
                                view.layoutParams = buttonLayoutParams

                                view.requestLayout()
                        //    }
                        }
                    }

                } catch (e: java.lang.Exception) {

                }*/


            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.e("  onScrolled ", dx.toString() + " " + dy.toString())

                recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect, view: View, parent: RecyclerView,
                        state: RecyclerView.State
                    ) {


                        /*             if (parent.getChildAdapterPosition(view) == parent.getAdapter()!!.getItemCount() - 3) {
                                         //for shadow
                                         outRect.bottom= 30
                                     }
                         */          Log.e("  NEW  ", "00000000000000000000000")
                        /*                       Log.e("parent.getChildViewHolder(view).layoutPosition", parent.getChildViewHolder(view).layoutPosition.toString())
                        Log.e("parent.getChildViewHolder(view).itemView.height.toString()", parent.getChildViewHolder(view).itemView.height.toString())
                        Log.e("parent.getChildItemId(view).toString()", parent.getChildItemId(view).toString())
                        Log.e("parent.getChildLayoutPosition(view).toString()",parent.getChildLayoutPosition(view).toString())
                        Log.e("parent.layoutManager!!.getPosition(view).toString()", parent.layoutManager!!.getPosition(view).toString())
*/
                        /*   if (parent.getChildAdapterPosition(view) == (parent.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()) {
                               outRect.top =-10 //-125/parent.getAdapter()!!.getItemCount() //parent.layoutManager.height
                           }*/

                        //if (parent.getChildViewHolder(view).layoutPosition ==1 || parent.getChildViewHolder(view).layoutPosition ==2 || parent.getChildViewHolder(view).layoutPosition ==3) {

                        if (dy < 0) {

                            Log.e("++++++++++++++++", outRect.top.toString())
                            outRect.top = outRect.top + 10
                            Log.e("++++++++++++++++", outRect.top.toString())
                            recyclerView.post {
                                adapter.notifyDataSetChanged()
                            }

                        }

                        /*   else {
                                Log.e("----------------", outRect.top.toString() )
                                outRect.top =  outRect.top -10 //-125/parent.getAdapter()!!.getItemCount() //parent.layoutManager.height
                                Log.e("----------------", outRect.top.toString() )
                                recyclerView.post {
                                    adapter.notifyDataSetChanged()
                                }
                            }
                        */

                        //     }


                        /*   if (parent.getChildAdapterPosition(view) == (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()) {
                               outRect.top =30 //-125/parent.getAdapter()!!.getItemCount() //parent.layoutManager.height
                           }*/
                    }
                })

            }


        })*/


//некрасивая прокрутка, нужны точные размеры recyclerview
//    var layoutManager: CarouselLayoutManager = CarouselLayoutManager(CarouselLayoutManager.VERTICAL)
            /* ryclerView.setLayoutManager(layoutManager)
    recyclerView.setHasFixedSize(true)
    recyclerView.addOnScrollListener( CenterScrollListener())
    layoutManager.setPostLayoutListener(object:CarouselLayoutManager.PostLayoutListener() {
        override fun transformChild(@NonNull child:View, itemPositionToCenterDiff:Float, orientation:Int):ItemTransformation {
            val scale = (1.5f * (1.5f * -StrictMath.atan(0.8) / Math.PI + 1)).toFloat()
            val translateY:Float
            val translateX:Float
            if (CarouselLayoutManager.VERTICAL === orientation)
            {
                val translateYGeneral = child.getMeasuredHeight() * (1 - scale) / 1f
                translateY = Math.signum(itemPositionToCenterDiff) * translateYGeneral
                translateX = 0f
            }
            else
            {
                val translateXGeneral = child.getMeasuredWidth() * (0.8f - scale) / 1.5f
                translateX = Math.signum(itemPositionToCenterDiff) * translateXGeneral
                translateY = 0f
            }
            return ItemTransformation(scale, scale, translateX, translateY)
        }
    })
*/


            //что-то что не вышло
            /* recyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener() {
        internal var ydy = 0
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            try {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (mLinearLayoutManager!!.orientation == LinearLayoutManager.VERTICAL) {
                        val positionScrolled =
                            if (itemToResize == 1) calculatePositionScrolledDown(recyclerView) else calculatePositionScrolledUp(
                                recyclerView
                            )
                        for (j in 0 until mLinearLayoutManager!!.itemCount - 1) {
                            val view = recyclerView.getChildAt(j)
                            view?.let { forceScrollItem(recyclerView, it, j, positionScrolled) }
                        }
                    }
                }
            } catch (e: java.lang.Exception) {
                Log.e(TAG, e.message!!)
            }


        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            heightCollapsedItem = 150
            heightExpandedItem = heightCollapsedItem * 3
            mLinearLayoutManager = linearLayoutManager
            try {
                if (linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                    dyAbs = Math.abs(dy)
                    val totalItemCount: Int = mLinearLayoutManager!!.getItemCount()
                    itemToResize = if (dy > 0) 1 else 0
                    initFocusResize(recyclerView)
                    calculateScrolledPosition(totalItemCount, recyclerView)
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
            }


        }
    })
*/

        */


  //          vr()


        return view
}





    private fun vr() {
        val datas: MutableList<String> = java.util.ArrayList()
        for (i in 0..14) {
            datas.add(i.toString())
        }
        val config = Config()
        config.secondaryScale = 1.0f
        config.scaleRatio = 0f
        //максимально видимых
        config.maxStackCount = 5
        config.initialStackCount = 6

        //скорость прокрутки
        config.parallex = 1.5f
        config.align = Align.TOP
        recyclerView.layoutManager= StackLayoutManager(config)
        recyclerView.setLayoutManager(StackLayoutManager(config))

        val context: Context? = getContext()
        val wm = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
       var width = display.getHeight();

       // config.space = (width/3.2/3*2).toInt()
        config.space = -100
            Log.e("oooooooooooooom", config.space.toString())


    }







  /*  fun onTouchEvent(event: MotionEvent): Boolean {
        var startX: Int = 0
        var startY: Int = 0
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x.toInt()
                startY = event.y.toInt()
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                startY = TODO("Could not convert int literal '0.0f' to Kotlin")
                startX = startY
            }
            MotionEvent.ACTION_MOVE -> if (getDistance(event) > touchSlop) {
                val x = event.x
                val y = event.y
                val direction: Direction = Direction.get(getAngle(startX, startY, x, y))
                onDirectionDetected(direction)
            }
        }
        return false
    }*/

    enum class Direction {
        UP, DOWN, LEFT, RIGHT;

        companion object {
            operator fun get(angle: Double): Direction {
                return if (inRange(angle, 45f, 135f)) {
                    UP
                } else if (inRange(angle, 0f, 45f) || inRange(angle, 315f, 360f)) {
                    RIGHT
                } else if (inRange(angle, 225f, 315f)) {
                    DOWN
                } else {
                    LEFT
                }
            }

            private fun inRange(angle: Double, init: Float, end: Float): Boolean {
                return angle >= init && angle < end
            }
        }
    }







    class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
        ) {
            if(parent.getChildAdapterPosition(view) == state.itemCount-1)
                outRect.bottom = spaceHeight
        }
    }
private fun calculateScrolledPosition(totalItemCount: Int, recyclerView: RecyclerView) {
    for (j in 0 until totalItemCount - 1) {
        val view = recyclerView.getChildAt(j)
        if (view != null) {
            if (recyclerView.getChildViewHolder(view) !is FocusResizeAdapter<*>.FooterViewHolder) {
                if (j == itemToResize) {
                    onItemBigResize(view, recyclerView)
                } else {
                    onItemSmallResize(view, recyclerView)
                }
                view.requestLayout()
            }
        }
    }
}

private fun onItemSmallResize(view: View, recyclerView: RecyclerView) {
    if (view.layoutParams.height - dyAbs <= heightCollapsedItem) {
        view.layoutParams.height = heightCollapsedItem
    } else if (view.layoutParams.height >= heightCollapsedItem) {
        view.layoutParams.height -= dyAbs * 2
    }
    adapter.onItemSmallResize(recyclerView.getChildViewHolder(view), itemToResize, dyAbs)
}


private fun onItemBigResize(view: View, recyclerView: RecyclerView) {
    if (view.layoutParams.height + dyAbs >= heightExpandedItem) {
        view.layoutParams.height = heightExpandedItem
    } else {
        view.layoutParams.height += dyAbs * 2
    }
    adapter.onItemBigResize(recyclerView.getChildViewHolder(view), itemToResize, dyAbs)
}

private fun initFocusResize(recyclerView: RecyclerView) {
    if (!init) {
        init = true
        val view = recyclerView.getChildAt(0)
        view.layoutParams.height = heightExpandedItem
        adapter.onItemInit(recyclerView.getChildViewHolder(view))
    }
}

private fun calculatePositionScrolledDown(recyclerView: RecyclerView): Int {
    val positionScrolled: Int
    if (mLinearLayoutManager!!.findFirstCompletelyVisibleItemPosition()
        == mLinearLayoutManager!!.itemCount - 1
    ) {
        positionScrolled = itemToResize - 1
        mLinearLayoutManager!!.scrollToPositionWithOffset(
            mLinearLayoutManager!!.findFirstVisibleItemPosition(), 0
        )
    } else {
        if (recyclerView.getChildAt(itemToResize).height > recyclerView.getChildAt(
                itemToResize - 1
            ).height
        ) {
            positionScrolled = itemToResize
            mLinearLayoutManager!!.scrollToPositionWithOffset(
                mLinearLayoutManager!!.findFirstCompletelyVisibleItemPosition(), 0
            )
        } else {
            positionScrolled = itemToResize - 1
            mLinearLayoutManager!!.scrollToPositionWithOffset(
                mLinearLayoutManager!!.findFirstVisibleItemPosition(), 0
            )
        }
    }
    return positionScrolled
}

private fun calculatePositionScrolledUp(recyclerView: RecyclerView): Int {
    val positionScrolled: Int
    if (recyclerView.getChildAt(itemToResize).height > recyclerView.getChildAt(itemToResize + 1)
            .height
    ) {
        positionScrolled = itemToResize
        mLinearLayoutManager!!.scrollToPositionWithOffset(
            mLinearLayoutManager!!.findFirstVisibleItemPosition(), 0
        )
    } else {
        positionScrolled = itemToResize + 1
        mLinearLayoutManager!!.scrollToPositionWithOffset(
            mLinearLayoutManager!!.findFirstCompletelyVisibleItemPosition(), 0
        )
    }
    return positionScrolled
}



private fun forceScrollItem(
    recyclerView: RecyclerView,
    view: View,
    j: Int,
    positionScrolled: Int
) {
    if (recyclerView.getChildViewHolder(view) !is FocusResizeAdapter<*>.FooterViewHolder) {
        if (j == positionScrolled) {
            view.layoutParams.height = heightExpandedItem
            adapter.onItemBigResizeScrolled(
                recyclerView.getChildViewHolder(view),
                itemToResize,
                dyAbs
            )
        } else {
            if (mLinearLayoutManager!!.findFirstCompletelyVisibleItemPosition()
                == mLinearLayoutManager!!.itemCount - 1
                || mLinearLayoutManager!!.findFirstCompletelyVisibleItemPosition() == -1
            ) {
                view.layoutParams.height = heightExpandedItem
                adapter.onItemBigResizeScrolled(
                    recyclerView.getChildViewHolder(view),
                    itemToResize,
                    dyAbs
                )
            } else {
                view.layoutParams.height = heightCollapsedItem
                adapter.onItemSmallResizeScrolled(
                    recyclerView.getChildViewHolder(view),
                    itemToResize,
                    dyAbs
                )
            }
        }
    }
}

/* override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
   super.
    //super.onScrollStateChanged(recyclerView, newState)
    try {
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (mLinearLayoutManager!!.orientation == LinearLayoutManager.VERTICAL) {
                val positionScrolled =
                    if (itemToResize == 1) calculatePositionScrolledDown(recyclerView) else calculatePositionScrolledUp(
                        recyclerView
                    )
                for (j in 0 until mLinearLayoutManager!!.itemCount - 1) {
                    val view = recyclerView.getChildAt(j)
                    view?.let { forceScrollItem(recyclerView, it, j, positionScrolled) }
                }
            }
        }
    } catch (e: java.lang.Exception) {
        Log.e(TAG, e.message!!)
    }
}*/


private fun fabOnClick() {
   val intent = Intent(context, AddSubActivityFragments::class.java)
   startActivityForResult(intent, newSubActivityRequestCode)
}

private fun layoutAnimation(recyclerView: RecyclerView){
var context: Context = recyclerView.context
var layoutController: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(
    context,
    R.anim.layout_recycler_down_to_up
)
recyclerView.layoutAnimation = layoutController
recyclerView.adapter?.notifyDataSetChanged()
recyclerView.scheduleLayoutAnimation()

}

/*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_search, menu)
    val searchItem = menu.findItem(R.id.searchView)
    val searchView = searchItem.actionView as SearchView
   //var context: Context? = getContext()
   //var subsListViewModel = ViewModelProvider(activity!!).get(SubListViewModel::class.java)

   searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
       override fun onQueryTextChange(newText: String?): Boolean {
           //      if (newText != null )
           //          subsListViewModel.***
           return true
       }

       override fun onQueryTextSubmit(query: String?): Boolean {
           return true
       }

   })

   super.onCreateOptionsMenu(menu, inflater)
  // return true
}*/

fun onQueryTextChange(query: String?): Boolean {
    // Here is where we are going to implement the filter logic
    return false
}

fun onQueryTextSubmit(query: String?): Boolean {
    return false
}



//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu, menu)
//        val searchItem = menu.findItem(R.id.action_search)
//        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        if (searchItem != null) {
//            searchView = searchItem.actionView as SearchView
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
//            queryTextListener = object : SearchView.OnQueryTextListener {
//                override fun onQueryTextChange(newText: String): Boolean {
//                    Log.i("onQueryTextChange", newText)
//                    return true
//                }
//
//                override fun onQueryTextSubmit(query: String): Boolean {
//                    Log.i("onQueryTextSubmit", query)
//                    return true
//                }
//            }
//            searchView.setOnQueryTextListener(queryTextListener)
//        }
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_search ->                 // Not implemented here
//                return false
//            else -> {
//            }
//        }
//        searchView.setOnQueryTextListener(queryTextListener)
//        return super.onOptionsItemSelected(item)
//    }














/* Opens FlowerDetailActivity when RecyclerView item is clicked. */
private fun adapterOnClick(sub: Sub) {

    var context: Context? = getContext()
    val options: ActivityOptionsCompat? = activity?.let {
        ActivityOptionsCompat.makeSceneTransitionAnimation(
            it,
            recyclerView,  // Starting view
            "anim" // The String
        )
    }

    val intent = Intent(context, SubDetailActivity()::class.java)
    intent.putExtra(SUB_ID, sub.id)
    startActivity(intent, options!!.toBundle())

}



}

class OverlapDecoration : RecyclerView.ItemDecoration() {
override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
) {
    val itemPosition = parent.getChildAdapterPosition(view!!)
    if (itemPosition == 0) {
        return
    }
    outRect[0, vertOverlap, 0] = 0
}

companion object {
    private const val vertOverlap = -80
}
}
class SimpleDividerItemDecorationLastExcluded(private val spacing: Int) :
RecyclerView.ItemDecoration() {

override fun getItemOffsets(
    rect: Rect,
    view: View,
    parent: RecyclerView,
    s: RecyclerView.State
) {
    parent.adapter?.let { adapter ->
        rect.right = when (parent.getChildAdapterPosition(view)) {
            RecyclerView.NO_POSITION,
            adapter.itemCount - 1 -> 0
            else -> spacing
        }
    }
}
}

class ItemDecorator(private val mSpace: Int) : RecyclerView.ItemDecoration() {
override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
) {
   val position = parent.getChildAdapterPosition(view)
   if (position != 0) outRect.top = mSpace

}
}



