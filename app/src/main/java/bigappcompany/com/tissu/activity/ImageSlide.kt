package bigappcompany.com.tissu.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import bigappcompany.com.tissu.R
import bigappcompany.com.tissu.adapter.DotsAdapter
import bigappcompany.com.tissu.adapter.ImageSlidePagerAdapter
import bigappcompany.com.tissu.model.DescriptinSliderModel

/**
 * @author Shankar
 * @created on 20 Sep 2017 at 11:36 AM
 */

class ImageSlide : AppCompatActivity() {

    private var mViewPager:ViewPager?=null
    private var dots:RecyclerView?=null
    private var previousPage: Int? = null
    private var previousState: Int? = null
    private var dotsAdapter: DotsAdapter? = null
    private var mImageSliderCustomAdapter: ImageSlidePagerAdapter? = null
    private var geasturesEnable: Boolean? = null
    private var cml:ArrayList<DescriptinSliderModel>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageslide)
        mapping()
    }

    private fun mapping() {

        mViewPager = findViewById(R.id.pager_zoom) as ViewPager
        dots = findViewById(R.id.dots) as RecyclerView

        mViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //Log.e("onPageScrool","position "+position+" , positionoffset "+positionOffset+" , positionoffsetPixels"+positionOffsetPixels);
            }

            override fun onPageSelected(position: Int) {
                Log.e("onPageSelected", "position " + position)
                if (previousPage != position) {
                    //changed

                    dotsAdapter!!.setSelected(position)
                    dotsAdapter!!.notifyDataSetChanged()
                }
                previousPage = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.e("onScrollStateChanged", "state " + state)

                previousState = state

            }
        })


        getSlidder()
    }

    private fun getSlidder() {
                    cml!!.clear()

                    mImageSliderCustomAdapter = ImageSlidePagerAdapter()
                    mImageSliderCustomAdapter!!.setData(cml, applicationContext, this@ImageSlide)
                    mImageSliderCustomAdapter!!.isDynamic(true)
                    mImageSliderCustomAdapter!!.setVP(mViewPager)
                    mViewPager!!.adapter = mImageSliderCustomAdapter
                    geasturesEnable = true
                    if (cml!!.size > 1) {
                        //Dots

                        with(dots!!, {
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL,
                                    false)
                        })
                        dotsAdapter = DotsAdapter(cml!!.size)
                        dots!!.setAdapter(dotsAdapter)
                    }
    }



}
