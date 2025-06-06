package com.example.william.my.module.compose.activity.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath

/**
 * ConstraintLayout
 */
@Route(path = RouterPath.Compose.ConstraintLayout)
class ConstraintLayoutActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConstraintLayoutExample()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ConstraintLayoutExample() {
        ConstraintLayout {
            // 初始化声明两个元素，如果只声明一个，则可用 createRef() 方法
            // 这里声明的类似于 View 的 id
            val (button1, button2, text1, text2, box1, box2, box3) = createRefs()

            Button(
                // constrainAs() 将 Composable 组件与初始化的引用关联起来
                // 关联之后就可以在其他组件中使用并添加约束条件了
                modifier = Modifier.constrainAs(button1) {
                    // 熟悉 ConstraintLayout 约束写法的一眼就懂
                    // parent 引用可以直接用，跟 View 体系一样
                    top.linkTo(parent.top, margin = 20.dp)
                    start.linkTo(parent.start, margin = 10.dp)

                }, onClick = {}) {
                Text("Button")
            }


            Text(text = "Text", Modifier.constrainAs(text1) {
                top.linkTo(button1.bottom, margin = 40.dp)

                // 将 Text 摆放在 ConstraintLayout 水平中间
                //centerHorizontallyTo(parent)

                // 将 Text 的中心摆放在 button1 右边界的位置
                centerAround(button1.end)
            })

            // Barrier
            // 设置一个 button1 和 text 右边的一个栅栏，将两者放在栅栏的左侧
            val barrier = createEndBarrier(button1, text1)

            Button(
                modifier = Modifier.constrainAs(button2) {
                    top.linkTo(parent.top, margin = 20.dp)
                    start.linkTo(barrier, margin = 10.dp)
                },
                onClick = {},
            ) {
                Text(text = "button2")
            }


            // Guide
            val guideline = createGuidelineFromTop(fraction = 0.5f)

            Text(text = "我距离屏幕上方约二分之一处~",
                modifier = Modifier.constrainAs(text2) {
                    top.linkTo(guideline)
                }
            )

            // Chain
            createHorizontalChain(box1, box2, box3, chainStyle = ChainStyle.SpreadInside)

            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Red)
                .constrainAs(box1) {
                    top.linkTo(text1.bottom)
                })
            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Green)
                .constrainAs(box2) {
                    top.linkTo(text1.bottom)
                })
            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Blue)
                .constrainAs(box3) {
                    top.linkTo(text1.bottom)
                })
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ConstraintSetExample() {
        BoxWithConstraints {
            val constraintSet = if (maxWidth < maxHeight) {
                // 竖屏
                decoupledConstraints(false)
            } else {
                // 横屏
                decoupledConstraints(true)
            }

            ConstraintLayout(constraintSet) {
                Button(
                    modifier = Modifier.layoutId("button"),
                    onClick = {},
                ) {
                    Text(text = "Button")
                }

                Text(
                    text = "Text",
                    Modifier.layoutId("text")
                )
            }
        }
    }

    /**
     * ConstraintSet
     */
    private fun decoupledConstraints(isPad: Boolean): ConstraintSet {
        return ConstraintSet {
            val button = createRefFor("button")
            val text = createRefFor("text")

            if (isPad) {
                // 横屏模式
                constrain(button) {
                    top.linkTo(parent.top, 15.dp)
                    start.linkTo(parent.start, 30.dp)
                }
                constrain(text) {
                    top.linkTo(parent.top, 15.dp)
                    start.linkTo(button.end, 20.dp)
                }
            } else {
                // 竖屏模式
                constrain(button) {
                    top.linkTo(parent.top, 30.dp)
                    start.linkTo(parent.start, 15.dp)
                }
                constrain(text) {
                    top.linkTo(button.bottom, 20.dp)
                    start.linkTo(parent.start, 15.dp)
                }
            }
        }
    }
}