# MPSCore
This library has the core class needed to build the [__MPSS__ (Multi Publish Subscribe Service)](https://github.com/blacktorch/MPSS)
and also create Java applications to publish and subscribe using __MPSS__.

## Creating a Multi Publish Subscribe Application
An MPS Application can be used to create publishers and subscribers. To create an
MPS Application, you would need the host address, and the port where MPSS is running.

````Java
class MPSExample {
    static MPSApplication mpsApplication = new MPSApplication("localhost", 12345);
}
````
An MPS Application can be treated as a single node or multiple nodes depending on the 
type of publisher or subscriber created. MPSCore gives the user the ability to create a 
single subscriber, or a single publisher application making the entire application to be 
treated as a single node by MPSService. You could also create a multiple publish-subscribe 
application, it all depends on your needs and resources.

### Creating a Publisher
You can create either a Single or a Multiple Publisher Application. Each publisher 
needs a subject or list of subjects to publish to, and these subjects need data.

````Java
class MPSExample {
    static MPSApplication mpsApplication = new MPSApplication("localhost", 12345);
    
    public static void main(String[] args) {
        Subject subject = new Subject("Location");
        JSONObject data = new JSONObject();
        data.put("lat", 224.456);
        data.put("lon", 553.453);
        subject.setData(data);
        /**
            You can use mpsApplication.createMultiAppPublisher(subject);
            to create a multi-plublisher application. A list of subjects
            can also be passed as arguments to both 'createMultiAppPublisher',
            and 'createSingleAppPublisher'. 
        **/
        Publisher publisher = mpsApplication.createSingleAppPublisher(subject);
        publisher.publishMessage();

    }
}
````

### Creating a Subscriber
You can create either a Single or a Multiple Subscriber Application. Each subscriber 
needs a subject or list of subjects to subscribe to, and to be able to receive the published
data you must implement the `INewSubscriberData` interface to listen for data being published
to the subjects subscribed to.
````Java
import com.chidiebere.nodes.Subscriber;class MPSExample implements INewSubscriberData {
    void onNewSubscriberData(Message message) {
        System.out.println(message.getData.get("Location"));
    }

    static MPSApplication mpsApplication = new MPSApplication("localhost", 12345);
    
    public static void main(String[] args) {
        Subject subject = new Subject("Location");

        /**
            You can use mpsApplication.createMultiAppSubscriber(subject);
            to create a multi-subscriber application. A list of subjects
            can also be passed as arguments to both 'createMultiAppSubscriber',
            and 'createSingleAppSubscriber'. 
        **/
        Subscriber subscriber = mpsApplication.createSingleAppSubscriber(subject);
        subscriber.attach();

    }
}
````
MPSCore makes it easy to create MPSService Subscribers and Publishers in Java.