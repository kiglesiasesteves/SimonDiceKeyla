import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

class MySurfaceView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d("MySurfaceView", "Surface created")
        // Start rendering thread or other initialization
        updateUI {
            // UI update logic here
            Log.d("MySurfaceView", "Updating UI on surface created")
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("MySurfaceView", "Surface changed: format=$format, width=$width, height=$height")
        // Handle surface changes
        updateUI {
            // UI update logic here
            Log.d("MySurfaceView", "Updating UI on surface changed")
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d("MySurfaceView", "Surface destroyed")
        // Stop rendering thread or other cleanup
        updateUI {
            // UI update logic here
            Log.d("MySurfaceView", "Updating UI on surface destroyed")
        }
    }

    private fun updateUI(action: () -> Unit) {
        Handler(Looper.getMainLooper()).post {
            action()
        }
    }
}