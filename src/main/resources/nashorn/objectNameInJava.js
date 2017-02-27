var callJavaClassPrinter = function() {

    var ObjectClassPrinter = Java.type('info.solidsoft.nashorn.util.ObjectClassPrinter');

    ObjectClassPrinter.print(77);
    //Play with different objects created in JavaScript
};
