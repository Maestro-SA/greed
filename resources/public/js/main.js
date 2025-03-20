// When plain htmx isn't quite enough, you can stick some custom JS here.

function app() {
    return {
        month: new Date().getMonth(),
        year: new Date().getFullYear(),
        DAYS: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
        MONTH_NAMES: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        blankdays: [],
        no_of_days: [],
        initDate() {
            let today = new Date();
            this.month = today.getMonth();
            this.year = today.getFullYear();
        },
        getNoOfDays() {
            let daysInMonth = new Date(this.year, this.month + 1, 0).getDate();
            let dayOfWeek = new Date(this.year, this.month).getDay();
            this.blankdays = Array(dayOfWeek).fill(null);
            this.no_of_days = Array.from({ length: daysInMonth }, (_, i) => i + 1);
        },
        openEventModal: false,
        event_title: '',
        event_date: '',
        event_theme: '',
        themes: ['Theme 1', 'Theme 2', 'Theme 3'],
        addEvent() {
            console.log('Event added:', this.event_title, this.event_date, this.event_theme);
            this.openEventModal = false;
        }
    }
}

let cardData = function() {
    return {
        countUp: function(target,startVal,endVal,decimals,duration){
            const countUp = new CountUp(target,startVal||0,endVal,decimals||0,duration||2);
            countUp.start();
        },
        sessions: [
            {
                "label": "Phone",
                "size": 60,
                "color": "indigo-600"
            },
            {
                "label": "Tablet",
                "size": 30,
                "color": "indigo-400"
            },
            {
                "label": "Desktop",
                "size": 10,
                "color": "indigo-200"
            }
        ]
    }
}
