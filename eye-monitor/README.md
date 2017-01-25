# eYe: Monitoring and Software Metrics

eYe is a simple application that enables the realtime monitoring of Spring Boot software.

## API

##### v2/featuresandno/{system}?client=


```JSON 
{  
   "EndPoint":{  
      "name":"EndPoint",
      "average":1771.0,
      "tags":[  
         "test"
      ],
      "featuresAndNoVersion":{  
         "0.0.0.1":{  
            "version":null,
            "accessNumber":8,
            "errorNumber":0,
            "errors":[  

            ]
         }
      },
      "timerList":[  
         18,
         9,
         1,
         0,
         0,
         0,
         2,
         14144
      ]
   },
   "Method of @Component":{  
      "name":"Method of @Component",
      "average":347.0,
      "tags":[  
         "test - componente"
      ],
      "featuresAndNoVersion":{  
         "0.0.0.1":{  
            "version":null,
            "accessNumber":7,
            "errorNumber":0,
            "errors":[  

            ]
         }
      },
      "timerList":[  
         0,
         0,
         0,
         0,
         0,
         0,
         2431
      ]
   }
}
 ```
 
##### v2/featuresandno/{system}?client=

```JSON 
  {"0.0.0.1":100.0}
``` 

##### v2/featureandpercentage/{system}?client=

```JSON 
  {"EndPoint":50.0,"Method of @Component":50.0}
``` 
