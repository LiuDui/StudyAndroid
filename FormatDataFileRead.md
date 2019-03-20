## XML
### Pull解析方式
```java

    public static void parseXMLWithPull(String xmlData){
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));

            // Returns the type of the current event (START_TAG, END_TAG, TEXT, etc.)
            int eventType = xmlPullParser.getEventType();

            String id = "";
            String name = "";
            String version = "";

            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
              //  Log.d("parseXMLWithPull", "nodeName = " + nodeName);
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)){
                            id = xmlPullParser.nextText();
                        }else if("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        }else if("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                        }else{
                            Log.d("parseXMLWithPull", "unknow tag");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("app".equals(nodeName)){
                            System.out.print("id :" + id);
                            System.out.print("name :" + name);
                            System.out.print("version :" + version);
                            Log.d("parseXMLWithPull", "id : " + id);
                            Log.d("parseXMLWithPull", "name :" + name);
                            Log.d("parseXMLWithPull", "version :" + version);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```
### SAX解析方式
...
## JSON
```xml
[{"id":"5", "version":"2.2", "name":"hhhh"},
    {"id":"6", "version":"2.4", "name":"heihei"}]
```
### 使用JSONbject
```java

    public static void parseJSONEithJSONObject(String jsonData){
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            int len = jsonArray.length();
            for (int i = 0; i < len; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");

                Log.d("parseJSONEithJSONObject", "id " + id);
                Log.d("parseJSONEithJSONObject", "name " + name);
                Log.d("parseJSONEithJSONObject", "version " + version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
```

### 使用GSON
也是采用的对象映射的方式。
对于单个json，比如：
```
{"name":"TOM", "age":20}
```
可以直接定义一个Person类，然后
```java
Gson gson = new Gson();
Person person = gson.fromJson(jsonData, Person.class);
```

一段json数组：
```java
Gson gson = new Gson();
List<Person> obs = gson.fromJson(jsonData, new TypeToken<List<Person>>(){}.getType());
```
