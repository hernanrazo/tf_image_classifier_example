package hernanrazo.tf_image_classifier_example;

import hernanrazo.tf_image_classifier_example.classifier.Recognition;
import java.util.List;

public interface resultsView {

    public void setResults(final List<Recognition> results);
}
