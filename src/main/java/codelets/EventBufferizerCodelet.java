package codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.representation.idea.Idea;
import environment.Environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class EventBufferizerCodelet extends Codelet {

    private Environment e;
    private Memory eventsMO;
    private Memory eventsBufferMO;
    private int buffer_size = 5;
    private List<Double> timestamp = new ArrayList<Double>();
    private List<Double> latitude = new ArrayList<Double>();
    private List<Double> longitude = new ArrayList<Double>();
    private Idea eventsBuffer = initializeBuffer();



    @Override
    public void accessMemoryObjects() {
        this.eventsMO=(MemoryObject)this.getInput("EVENTS");
        this.eventsBufferMO=(MemoryObject)this.getOutput("EVENTS_BUFFER");
    }

    @Override
    public void proc() {
        if (this.eventsMO.getI()=="")    {
            return;
        }

        Idea eventFrame = (Idea) eventsMO.getI();
        List<Idea> eventFrames = (List<Idea>) ((Idea) this.eventsBuffer).getValue();
        addFrame(eventFrames, eventFrame);
        eventsBufferMO.setI((Idea) eventsBuffer);

//          Print that checks if buffer correctly shifts value positions
        System.out.println(((Idea) eventsBufferMO.getI()).getValue());

    }

    @Override
    public void calculateActivation() {

    }

    public Idea initializeBuffer() {
        Idea eventsBuffer = new Idea("eventsBuffer","",0);
        List<Idea> events = new ArrayList<Idea>();
        eventsBuffer.setValue(events);
        return eventsBuffer;
    }

    private void addFrame(List<Idea> events, Idea event) {
        if (events.size()<this.buffer_size) {
            events.add(event);
        } else {
            events.remove(0);
            events.add(event);
        }

    }

}
