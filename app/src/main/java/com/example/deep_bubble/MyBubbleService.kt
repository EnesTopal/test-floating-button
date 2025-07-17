package com.example.deep_bubble

import android.content.Intent
import com.torrydo.floatingbubbleview.CloseBubbleBehavior
import com.torrydo.floatingbubbleview.helper.NotificationHelper
import com.torrydo.floatingbubbleview.service.expandable.BubbleBuilder
import com.torrydo.floatingbubbleview.service.expandable.ExpandableBubbleService
import com.torrydo.floatingbubbleview.service.expandable.ExpandedBubbleBuilder
import android.os.IBinder // Used internally, just for context
import android.util.Log
import android.view.KeyEvent
import com.torrydo.floatingbubbleview.FloatingBubbleListener
import com.torrydo.floatingbubbleview.helper.ViewHelper

class MyFloatingBubbleService : ExpandableBubbleService() {

    private var isExpanded = false

    override fun onCreate() {
        super.onCreate()
//        expand()
        minimize() // Start in the minimized state
        isExpanded = false
    }

    override fun configBubble(): BubbleBuilder? {

        val closeIconView = ViewHelper.fromDrawable(
            this,
            R.drawable.ic_close_bubble,
            60,
            60
        )
        val bubbleIconView = ViewHelper.fromDrawable(
            this,
            R.drawable.ic_rounded_blue_diamond, // Your icon
            100,
            100
        )

        bubbleIconView.setOnClickListener {
            toggleView()
        }


        return BubbleBuilder(this)
            .bubbleView(bubbleIconView)
//            .bubbleCompose {
//                BubbleCompose(expand = { toggleView() })
//            }
            .forceDragging(true)
            .bubbleStyle(null)
            .startLocation(300, 300)
            .enableAnimateToEdge(true)
            .closeBubbleView(closeIconView)
            .closeBubbleStyle(null  )
            .closeBehavior(CloseBubbleBehavior.FIXED_CLOSE_BUBBLE)
            .distanceToClose(80)
            .bottomBackground(true)
            .bubbleDraggable(true)
    }

    override fun configExpandedBubble(): ExpandedBubbleBuilder? {
        return ExpandedBubbleBuilder(this)
            .expandedCompose {
                ExtendedComposeView(minimize = { toggleView() })
            }
            .onDispatchKeyEvent { keyEvent ->
                if (keyEvent.keyCode == KeyEvent.KEYCODE_BACK && isExpanded) {
                    toggleView()
                    return@onDispatchKeyEvent true
                }
                false
            }
            .startLocation(0, 50)
            .draggable(true)
            .fillMaxWidth(false)
            .enableAnimateToEdge(true)
            .dimAmount(0.5f)
    }

    private fun toggleView() {
        if (isExpanded) {
            Log.d("MyBubble", "Minimizing")
            minimize()
        } else {
            Log.d("MyBubble", "Expanding")
            expand()
        }
        isExpanded = !isExpanded
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}