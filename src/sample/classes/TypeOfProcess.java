package sample.classes;

public enum TypeOfProcess {
    NEW{
        @Override
        public String toString(){
            return "Waiting";
        }
    },
    RUNNING{
        @Override
        public String toString(){
            return "Running";
        }
    },
    WAITING{
        @Override
        public String toString(){
            return "Waiting";
        }
    },
    READY{
        @Override
        public String toString(){
            return "Ready";
        }
    },
    TERMINATED{
        @Override
        public String toString(){
            return "Finished";
        }
    },
    REJECTED{
        @Override
        public String toString(){return "Rejected"; }
    }
}
