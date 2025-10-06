This project was built using Java that demonstrates external API integration to fetch and display real-time currency exchange rates. 
It moves beyond basic logic by connecting to the internet, proving proficiency in core modern development skills:

1. Networking: Uses Java's HttpURLConnection to make external requests.
2. API Integration: Communicates with the ExchangeRate-API to get live data.
3. JSON Parsing: Employs the GSON library to instantly translate the raw data stream into usable Java objects for calculation.

To compile the code, run the below code in your terminal: 
javac -cp "lib/gson-2.13.2.jar" CurrencyConverter.java

Followed by: 
java -cp "lib/gson-2.10.1.jar;." CurrencyConverter
