package lab.wavgrafx.com.dumaguetehealthline

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import lab.wavgrafx.com.dumaguetehealthline.adapter.CustomPagerAdapter

class Fitness : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitness)

        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager
        viewPager.adapter = CustomPagerAdapter(this)
        viewPager.setPageTransformer(true, FadeOutTransformation())
    }

    inner class FadeOutTransformation : ViewPager.PageTransformer {
        override fun transformPage(page: View, position: Float) {

            page.translationX = -position * page.width

            page.alpha = 1 - Math.abs(position)


        }
    }
}
