## Wart Immunotherapy Prediction App
"Wart Immunotherapy Prediction App" is a medical decision support system helping doctors decide whether Immunotherapy is the right treatment 
method for their patients. Based on the given information about the patient - age, gender, time before treatment,
number of warts, type of warts, surface area of the biggest wart and induration diameter of initial test - the app 
predicts the result of the of the treatment.

Predictions are based on a model (decision tree) trained using J48 algorithm with 10-fold cross-validation and ["Weka 3: Machine Learning Software in Java"](https://www.cs.waikato.ac.nz/ml/weka/).

The dataset that used for training and testing the model can be found [here](https://archive.ics.uci.edu/ml/datasets/Immunotherapy+Dataset).

**[Report](https://github.com/p17griv/wart-immunotherapy-prediction-app/blob/master/report.pdf)** (Greek)

---

### Download the APK
for Android 4.3 Jelly Bean or newer version [here](https://github.com/p17griv/wart-immunotherapy-prediction-app/blob/master/wart_immunotherapy_prediction.apk).

![Image of the App](https://github.com/p17griv/wart-immunotherapy-prediction-app/blob/master/imgs/app_sample.png)

---

### DISCLAIMER
<b>The final decision should not be 100% based on the app's prediction!</b>

The app's accuracy is 76.3% and is correctly predicting A* 68.4% and B** 84.2% of time.

*Immunotherapy is the right method cases.

**Immunotherapy is not the right method cases.

![J48 Results](https://github.com/p17griv/wart-immunotherapy-prediction-app/blob/master/imgs/j48_balanced.png)

---
## License

See the [LICENSE](LICENSE) file for license rights and limitations (MIT).
