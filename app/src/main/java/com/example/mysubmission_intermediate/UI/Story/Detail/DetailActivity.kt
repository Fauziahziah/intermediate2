package com.example.mysubmission_intermediate.UI.Story.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmission_intermediate.Api.StoryResponseItem
import com.example.mysubmission_intermediate.R
import com.example.mysubmission_intermediate.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.detail_story)
            setDisplayHomeAsUpEnabled(true)
        }

        setupData()
    }

    private fun setupData() {
        val detail = intent.getParcelableExtra<StoryResponseItem>(EXTRA_DATA) as StoryResponseItem
        binding.apply {
            tvUsername.text = detail.name
            tvDetailStory.text = detail.description
            Glide.with(this@DetailActivity)
                .load(detail.photoUrl)
                .fitCenter()
                .apply(
                    RequestOptions
                        .placeholderOf(R.drawable.ic_baseline_refresh_24)
                        .error(R.drawable.ic_baseline_broken_image_24)
                ).into(ivDetailStory)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

}