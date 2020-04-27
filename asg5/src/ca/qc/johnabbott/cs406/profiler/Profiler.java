package ca.qc.johnabbott.cs406.profiler;

import java.util.*;

/**
 * A simple profiling class.
 */
public class Profiler {

    /*
    Delimits a section or region of the profiling.
    */
    private static class Mark {

        // stores the type of mark
        private enum Type {
            START_REGION, END_REGION, START_SECTION, END_SECTION
        }

        public Type type;
        public long time;
        public String label;

        // Create mark without a label
        public Mark(Type type, long time) {
            this(type, time, null);
        }

        // Create a mark with a label
        public Mark(Type type, long time, String label) {
            this.type = type;
            this.time = time;
            this.label = label;
        }

        @Override
        public String toString() {
            return "Mark{" +
                    "type=" + type +
                    ", time=" + time +
                    ", label='" + label + '\'' +
                    '}';
        }
    }

    // store singleton instance
    private static Profiler INSTANCE;
    static {
        INSTANCE = new Profiler();
    }

    /**
     * Get profiler singleton instance.
     * @return the profiler singleton.
     */
    public static Profiler getInstance() {
        return INSTANCE;
    }

    // store marks in list
    private List<Mark> marks;

    // use to prevent regions when not wanted/needed.
    private boolean paused;
    private boolean inSection;

    // private constructor for singleton
    private Profiler() {
        // linked list, because append is a constant time operation.
        marks = new LinkedList<>();
        paused = false;
        inSection = false;
    }

    /**
     * Starts a new profiling section.
     * @param label The section label.
     */
    public void startSection(String label) {
        if(!paused) {
            marks.add(new Mark(Mark.Type.START_SECTION, System.nanoTime(), label));
            inSection = true;
        }
    }

    /**
     * Ends a section. Must be paired with a corresponding call to `startSection(..)`.
     */
    public void endSection() {
        if(!paused) {
            marks.add(new Mark(Mark.Type.END_SECTION, System.nanoTime()));
            inSection = false;
        }
    }

    /**
     * Starts a new profiling region.
     * @param label The region label.
     */
    public void startRegion(String label) {
        if(!paused && inSection)
            marks.add(new Mark(Mark.Type.START_REGION, System.nanoTime(), label));
    }

    /**
     * Ends a region. Must be paired with a corresponding call to `startRegion(..)`.
     */
    public void endRegion() {
        if(!paused && inSection)
            marks.add(new Mark(Mark.Type.END_REGION, System.nanoTime()));
    }

    /**
     * Using the currently collected data, generate all the section data for reporting.
     * @return A list of sections.
     */
    public List<Section> produceSections()   {
        List<Section> sections = new ArrayList<>();
        Stack<Mark> markStack = new Stack<>();
        Map<String, Region>  stringRegionMap = new HashMap<>();
        Region region;

        Mark mark;
        Section section = null;

        String label;
        Long timeTaken;
        Long totalTime;
        Double sectionPercent;

        Iterator iterator;

        for(Mark myMark: marks) {

            switch (myMark.type) {
                case START_SECTION:
                    //push start of mark to stack
                    markStack.push(myMark);
                    //define label for that section
                    section = new Section(myMark.label);

                    //set region label
                    stringRegionMap.put(section.getSectionLabel(), new Region(section, 0, 0L, 1d));
                    break;
                case END_SECTION:
                    if (markStack.isEmpty()) throw new EmptyStackException(); // is Stack empty
                    //record most recent mark (the last one)
                    mark = markStack.pop();
                    //calculate time taken
                    totalTime = timeTaken = myMark.time - mark.time;
                    //add elapsed time to region
                    stringRegionMap.get(section.getSectionLabel()).addElapsedTime(timeTaken);
                    //add run count to region
                    stringRegionMap.get(section.getSectionLabel()).addRun();
                    iterator = stringRegionMap.keySet().iterator();
                    Object key;

                    while (iterator.hasNext()) {
                        key = iterator.next();      //grab the next key
                        region = stringRegionMap.get(key);  //save the keyed region
                        timeTaken = region.getElapsedTime(); //save the elapsed time of that region object
                        sectionPercent = (double) timeTaken / (double) totalTime; //calculate the percentage time within the section
                        region.setPercentOfSection(sectionPercent); //set the percent of section

                        //add the region with its label or if it is the section than add it as "TOTAL"
                        section.addRegion(region != stringRegionMap.get(section.getSectionLabel()) ? key.toString() : "TOTAL", region);
                    }
                    //clear map for new section
                    stringRegionMap = new HashMap<>();
                    sections.add(section);
                    break;

                case START_REGION:
                    //record mark to stack (by pushing)
                    markStack.push(myMark);

                    label = myMark.label;
                    //if the region has already been ran, increment the run count;
                    if (stringRegionMap.containsKey(label))
                        stringRegionMap.get(label).addRun();
                    else
                        stringRegionMap.put(label, new Region(section, 1, 0L, 0d));
                    break;

                case END_REGION:
                    if (markStack.isEmpty()) throw new EmptyStackException(); //is stack Empty

                    mark = markStack.pop(); //pop most recent mark and save it
                    label = mark.label; //save the label name
                    region = stringRegionMap.get(label); //save the region object for efficiency
                    timeTaken = myMark.time - mark.time; //find time taken
                    region.addElapsedTime(timeTaken); //add it to the mark's time
                    break;
            }
        }
        //clear the marks list
        marks.clear();
        return sections;
    }
    
    @Override
    public String toString() {
        // print all marks using formatting.
        StringBuilder builder = new StringBuilder();
        for(Mark mark : marks)
            builder.append(String.format("%d %13s %-30s\n", mark.time, mark.type.toString(), mark.label != null ? mark.label : ""));
        return builder.toString();
    }
}