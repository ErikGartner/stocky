# Stocky

[![Codeship Status for Stocky](https://codeship.com/projects/7d9d7560-8a2f-0132-c7fa-7acbd47feca1/status)](https://codeship.com/projects/59952)  

**DEPRECATED:** *This project is old and does not provide tradeable results. Use only for inspiration and code examples.*

Stocky is an attempt at a stock prediction application built ontop of JavaML and WEKA.
It combines a lot of different algorithms and metrics.

## Code Example

The application is run by executing the StockyMain class. It will then load the options stored in
config.json. Below is an example config:

```json
{
    "symbols": [
        "AAPL",
        "AXS",
        "^OMX"
    ],
    "predictors": [
        "mlp"
    ],
    "pushoverRecipients": [
        ""
    ],
    "pushoverKey": ""
    ,
    "usedMetrics": [
        "mean_close",
        "high_to_mean",
        "mean_volume"
    ],
    "start": "2000-01-01",
    "periodSize": 5,
    "bootstrapp": false
}

```

* symbols - a list of stock identifiers
* predictor - what predictors to use
* pushoverRecipient - a list of Pushover user keys to send the results to
* pushoverKey - a Pushover API key
* usedMetrics - a list of the metrics to use in calculations
* start - the first date to get stock data from until now or finish
* periodSize - group days into periods, i.e. weeks, month etc.
* bootstrap - true if the program shall combine the predictors and bootstrap them

## Installation

The project uses maven to download all dependencies and all external libraries are included.

### Third-party libraries
Stocky build on top of several open source libraries:

* JavaML
* Weka
* GSON
* Joda-Time
* Pushover4j

## Tests

JUnit tests are kept in src/test.

## Contributors

Reach me at @erik_gartner.

## License

Stocky is released under The MIT License (MIT).

Copyright (c) 2015 Erik Gärtner

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
