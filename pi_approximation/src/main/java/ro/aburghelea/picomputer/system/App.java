package ro.aburghelea.picomputer.system;

import ro.aburghelea.picomputer.entry.Computer;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/2/2014
 */
public class App {


    public static void main(String[] args) {
        Computer pi = new Computer();
        pi.calculate(8, 160000, 10000);
    }
}
