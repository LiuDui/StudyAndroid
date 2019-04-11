## Rect和RectF
Rect与RectF都是用来创建一个矩形的。

* Rect的参数是int类型
* RectF的参数是float类型（精度较Rect更高）
* 都是通过四个坐标参数来确定矩形区域

## invalidate和postInvalidate
都是用来刷新界面的，在UI主线程中，用`invalidate()`，本质是调用View的`onDraw()`绘制，
在其他线程中，调用`postInvalidate()`，
