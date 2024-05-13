import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {

    private JSONObject weatherData;
    public WeatherAppGui() {
        // setup gui and add title
        super("Weather App");

        //configure gui to end the program once closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set the size of our gui
        setSize(425, 650);

        //load gui center of screen
        setLocationRelativeTo(null);

        //manually position gui
        setLayout(null);

        //prevent resize
        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents() {
        // search
        JTextField searchTextField = new JTextField();

        //set the location and size of component
        searchTextField.setBounds(15,15,351,45);

        //change the font style and size
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);

        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0,125,425,217);
        add(weatherConditionImage);

        JLabel temperatureText = new JLabel("50 F");
        temperatureText.setBounds(0,350,425,54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD,48));

        //center the text
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        //weather condition description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0,405,425,36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN,32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        //humidity image
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15,500,74,66);
        add(humidityImage);

        //humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90,500,85,55);
        humidityText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(humidityText);

        //windspeed image
        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(220,500,74,66);
        add(windSpeedImage);

        JLabel windSpeedText = new JLabel("<html><b>Windspeed</b> 15mph</html>");
        windSpeedText.setBounds(310,500,90,55);
        windSpeedText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(windSpeedText);

        // search button
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        //change cursor when hovering
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(370,13,45,45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextField.getText();

                //validate input
                if(userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }

                weatherData = WeatherApp.getWeatherData(userInput);

                //update image
                String weatherCondition = (String) weatherData.get("weather_condition");

                switch(weatherCondition) {
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }

                //update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " F");

                weatherConditionDesc.setText(weatherCondition);

                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                //still needs work
                //update windspeed
                double windspeed = (double) weatherData.get("wind_speed");
                windSpeedText.setText("<html><b>Windspeed</b> " + windspeed + "mph</html>");
            }
        });
        add(searchButton);
    }

    private ImageIcon loadImage(String resourcePath) {
        try{
            //reads the image from the path given
            BufferedImage image = ImageIO.read(new File(resourcePath));

            //returns image icon so it can be read
            return new ImageIcon(image);
        }catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println("Could not find resource file.");
        return null;
    }

}