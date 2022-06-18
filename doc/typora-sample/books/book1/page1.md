An h2 header 001
------------

Paragraphs are separated by a blank line.

2nd paragraph. *Italic*, **bold**, and `monospace`. Itemized lists
look like:

  * this one
  * that one
  * the other one

Note that --- not considering the asterisk --- the actual text
content starts at 4-columns in.

> Block quotes are
> written like so.
>
> They can span multiple paragraphs,
> if you like.

 

```java
package loadinganxmlfile;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class LoadingAnXMLFile {
    public static void main(String[] args) {
        Document dom;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        try {
            // Write code that can throw errors here...
            DocumentBuilder builder = factory.newDocumentBuilder();
            dom = builder.parse("cars.xml");
            
            PrintXmlDocument(dom);
        }
        catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } 
        catch (SAXException se) {
            System.out.println(se.getMessage());
        } 
        catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }
    
}
```

As you probably guessed, indented 4 spaces. By the way, instead of
indenting the block, you can use delimited blocks, if you like:

~~~bash
#!/usr/bin/env bash
echo -n "Enter The Number: "
read -r a
fact=1
while [ "$a" -ne 0 ]; do
	fact=$((fact * a))
	a=$((a - 1))
done
echo $fact
~~~



## An h2 header ## 002



<img src="https://raw.githubusercontent.com/daodaovps/blog-img-at-github/master/imgs/2022/06/12/20220612-165102.jpg" style="zoom:33%;" />  



