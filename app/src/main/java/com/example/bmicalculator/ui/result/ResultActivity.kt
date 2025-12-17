package com.example.bmicalculator.ui.result

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.bmicalculator.R
import com.example.bmicalculator.data.history.HistoryItem
import com.example.bmicalculator.data.history.HistoryRepository
import com.example.bmicalculator.data.history.PrefsHistoryStorage
import com.example.bmicalculator.databinding.ActivityResultBinding
import com.example.bmicalculator.model.BmiResult
import com.example.bmicalculator.ui.history.HistoryActivity
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = getResult() ?: return

        saveHistory(result)
        animateBmi(result.bmi)
        animateProgress(result.bmi)
        applyCategory(result)

        binding.btnShare.setOnClickListener {
            shareAsImage()
        }

        binding.btnViewHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    /* -------- Safe Parcelable -------- */

    private fun getResult(): BmiResult? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("BMI_RESULT", BmiResult::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("BMI_RESULT")
        }
    }

    /* -------- Save History -------- */

    private fun saveHistory(result: BmiResult) {
        val repo = HistoryRepository(PrefsHistoryStorage(this))
        val date = SimpleDateFormat("dd MMM yyyy", Locale.US).format(Date())

        repo.save(
            HistoryItem(
                bmi = result.bmi,
                category = result.category,
                date = date
            )
        )
    }

    /* -------- Animations -------- */

    private fun animateBmi(bmi: Double) {
        val animator = ValueAnimator.ofFloat(0f, bmi.toFloat())
        animator.duration = 700
        animator.addUpdateListener {
            binding.tvBmiValue.text =
                String.format(Locale.US, "%.1f", it.animatedValue as Float)
        }
        animator.start()
    }

    private fun animateProgress(bmi: Double) {
        val progress = ((bmi / 40.0) * 100).coerceAtMost(100.0)
        val animator = ValueAnimator.ofInt(0, progress.roundToInt())
        animator.duration = 700
        animator.addUpdateListener {
            binding.progressBMI.progress = it.animatedValue as Int
        }
        animator.start()
    }

    private fun applyCategory(result: BmiResult) {
        binding.tvBmiCategory.text = result.category
        binding.tvBmiDesc.text = result.description

        val color = when (result.category) {
            "UNDERWEIGHT" -> getColor(R.color.bmi_underweight)
            "NORMAL" -> getColor(R.color.bmi_normal)
            "OVERWEIGHT" -> getColor(R.color.bmi_overweight)
            else -> getColor(R.color.bmi_obese)
        }

        binding.tvBmiCategory.setTextColor(color)
        binding.progressBMI.setIndicatorColor(color)
    }

    /* -------- Share as Image (RESULT ONLY) -------- */

    private fun shareAsImage() {
        val bitmap = captureView(binding.resultContainer)
        val uri = saveBitmap(bitmap) ?: return

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        startActivity(Intent.createChooser(intent, "Share BMI Result"))
    }

    private fun captureView(view: View): Bitmap {
        val bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmap(bitmap: Bitmap): android.net.Uri? {
        return try {
            val cachePath = File(cacheDir, "images")
            cachePath.mkdirs()

            val file = File(cachePath, "bmi_result.png")
            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }

            FileProvider.getUriForFile(
                this,
                "$packageName.fileprovider",
                file
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
