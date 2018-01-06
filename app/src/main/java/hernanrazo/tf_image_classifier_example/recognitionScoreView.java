package hernanrazo.tf_image_classifier_example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import java.util.List;
import hernanrazo.tf_image_classifier_example.classifier.Recognition;


public class recognitionScoreView extends View implements resultsView {

    private static final float TEXT_SIZE_DIP = 24;
    private List<Recognition> results;
    private final float textSizePx;
    private final Paint fgPaint;
    private final Paint bgPaint;

    public recognitionScoreView(final Context context, final AttributeSet set) {
        super(context, set);

        textSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                TEXT_SIZE_DIP,
                getResources().getDisplayMetrics());

        fgPaint = new Paint();
        fgPaint.setTextSize(textSizePx);

        bgPaint = new Paint();
        bgPaint.setColor(Color.rgb(51, 255, 94));
    }

    @Override
    public void setResults(final List<Recognition> results) {
        this.results = results;
        postInvalidate();
    }

    @Override
    public void onDraw(final Canvas canvas) {
        final int x = 10;
        int y = (int) (fgPaint.getTextSize() * 1.5f);

        canvas.drawPaint(bgPaint);

        if (results != null) {
            for (final Recognition recog : results) {
                canvas.drawText(recog.getTitle() + ": " + recog.getConfidence(), x, y, fgPaint);
                y += fgPaint.getTextSize() * 1.5f;
            }
        }
    }
}