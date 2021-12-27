# ibkr-short-inventory-client

To find out more on what this client returns, please read https://ibkr.info/article/2024

## Usage

```
    ShortInventoryClient shortInventoryClient = new ShortInventoryClient();
    Map<String, ShortInventoryData> shortInventoryDataMap = shortInventoryClient.shortInventoryData(Region.USA);
    ShortInventoryData shortInventoryData = shortInventoryDataMap.get("AAPL");
    System.out.println(shortInventoryData.toString());
```
Output:
```****
    ShortInventoryData(symbol=AAPL, availableShares=10000000, isGreaterThan=true)
```
In other words, for AAPL on the day of the request, there are over 10,000,000 shares available to short 
at Interactive Brokers

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate (there currently are none but feel free to start contributing!)

## License
[MIT](https://choosealicense.com/licenses/mit/)