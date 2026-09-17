package com.nuvio.app.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.rightClick
import androidx.compose.ui.test.click
import androidx.compose.ui.unit.dp
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PosterCardClickableDesktopTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun secondaryClickTriggersLongClickAndStashesAnchorOnDesktop() {
        var clicked = false
        var longClicked = false

        compose.setContent {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .testTag("card")
                    .posterCardClickable(
                        onClick = { clicked = true },
                        onLongClick = { longClicked = true },
                        zoomImageUrl = "https://example.com/poster.jpg",
                    ),
            )
        }

        compose.waitForIdle()
        compose.onNodeWithTag("card").performMouseInput {
            rightClick()
        }
        compose.waitForIdle()

        assertTrue(longClicked, "Right-click should trigger onLongClick on desktop")
        val anchor = PosterZoomAnchorHolder.consume()
        assertNotNull(anchor, "Anchor should be stashed in PosterZoomAnchorHolder")
        assertEquals("https://example.com/poster.jpg", anchor.imageUrl)
    }

    @Test
    fun primaryClickTriggersOnClickOnDesktop() {
        var clicked = false
        var longClicked = false

        compose.setContent {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .testTag("card")
                    .posterCardClickable(
                        onClick = { clicked = true },
                        onLongClick = { longClicked = true },
                    ),
            )
        }

        compose.waitForIdle()
        compose.onNodeWithTag("card").performMouseInput {
            click()
        }
        compose.waitForIdle()

        assertTrue(clicked, "Primary click should trigger onClick on desktop")
    }
}
