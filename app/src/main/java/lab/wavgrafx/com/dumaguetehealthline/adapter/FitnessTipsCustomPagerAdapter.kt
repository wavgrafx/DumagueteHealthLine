package lab.wavgrafx.com.dumaguetehealthline.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import lab.wavgrafx.com.dumaguetehealthline.model.FitnessTipsModel

class CustomPagerAdapter(private val mContext: Context) : PagerAdapter() {
    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = FitnessTipsModel.values()[position]
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup
        collection.addView(layout)
        return layout
    }
    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }
    override fun getCount(): Int {
        return FitnessTipsModel.values().size
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
    override fun getPageTitle(position: Int): CharSequence {
        val customPagerEnum = FitnessTipsModel.values()[position]
        return mContext.getString(customPagerEnum.titleResId)
    }
}