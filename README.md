Tensorflow Image Classifier
===

This project is an image classifier Android app that uses TensorFlow to distinguish between pens, calculators, and phones. This project uses a convolutional neural network, specifically, the Google Inception model.  

To train this model to recognize pens, calculators, and phones, you can use a method called transfer learning. The original inception model already has layers that do most of the heavy work, To make a custom classifier, you only have to change the last few to get good results.   

To do this, first download a large amount of images for whatever object you want to detect. Use your preferred Chrome extension to bulk download any images off a Google Images search or other webpages. After that, place all the images in a folder and name that folder properly with the object that the images represent. This is important since the finished product will use these labels to display results. You can do this for multiple objects if you want to classify more than one thing.  

To actually retrian the model, open a Docker container and place a TensorFlow image into it. After that, pull the code from the TensorFlow directory. This code will train the model to recoginze the images you feed it. Depending on the number of images and what computer you are using, this may take a while. The better the resulting accuracy, the better the model will be.  

Finally, classify each image by using the following code:  

```python
import tensorflow as tf
import sys

image_path = sys.argv[1]
image_data = tf.gfile.FastGFile(image_path, 'rb').read()
label_lines = [line.rstrip() for line 
                in tf.gfile.GFile("retrained_labels.txt")]

with tf.gfile.FastGFile("retrained_graph.pb", 'rb') as f:
    graph_def = tf.GraphDef()
    graph_def.ParseFromString(f.read())
    _ = tf.import_graph_def(graph_def, name = '')
    
with tf.Session() as sess:
    softmax_tensor = sess.graph.get_tensor_by_name('final_result:0')
    
    predictions = sess.run(softmax_tensor, \
            {'DecodeJpeg/contents:0': image_data})
    
    top_k = predictions[0].argsort()[-len(predictions[0]):][::-1]
    
    for node_id in top_k:
        human_string = label_lines[node_id]
        score = predictions[0][node_id]
        print('%s (score = %.5f)' % (human_string, score))


```

In Android Studio, I placed the following code in the `classifierActivity.java` file to connect the new model to the app:  

```java

private static final String MODEL_FILE = "file:///android_asset/rounded_graph.pb";

private static final String LABEL_FILE = "file:///android_asset/retrained_labels.txt";
```

Thus, an example would look like this:

<img src="https://github.com/hrazo7/tf_image_classifier_example/blob/master/other/screenshots/phone.png" width="400" height="650" title="phone">   

<img src="https://github.com/hrazo7/tf_image_classifier_example/blob/master/other/screenshots/pen.png" width="400" height="650" title="pen">

<img src="https://github.com/hrazo7/tf_image_classifier_example/blob/master/other/screenshots/calculator.png" width="400" height="650" title="calculator">

Sources and Helpful Links
---

http://nilhcem.com/android/custom-tensorflow-classifier
https://www.youtube.com/watch?v=QfNvhPx5Px8
https://github.com/google/inception