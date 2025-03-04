import { style } from "https://js.sabae.cc/stdom.js";
import { Day } from "https://code4fukui.github.io/day-es/Day.js";
import { isHoliday } from "https://code4fukui.github.io/day-es/Holiday.js";
import { Cal } from "https://code4fukui.github.io/day-es/examples/day-calendar.js"; // reference

class InputDates extends HTMLElement {
  constructor() {
    super();
    const cr = (tag) => document.createElement(tag);
    this.style.display = "inline-block";

    const monthmode = true;
    this.monthmode = monthmode;
    const ndays = monthmode ? 7 * 7 : 366 + 5;
    this.day = new Day();
    this.checked = [];

    const h2 = cr("div");
    h2.style.fontSize = "1.3em";
    h2.style.textAlign = "center";
    h2.style.margin = ".2em 0";
    const prev = cr("span");
    prev.textContent = "◀";
    prev.onclick = () => {
      this.prevMonth();
      if (this.onchangeMonth) {
        this.onchangeMonth();
      }
    };
    h2.appendChild(prev);
    const center = cr("span");
    center.className = "day";
    h2.appendChild(center);
    this.yearmonth = center;
    center.style.display = "inline-block";
    center.style.width = "6em";
    this.appendChild(h2);
    const next = cr("span");
    next.textContent = "▶";
    h2.appendChild(next);
    next.onclick = () => {
      this.nextMonth();
      if (this.onchangeMonth) {
        this.onchangeMonth();
      }
    };

    const div = cr("div");
    this.caldiv = div;
    div.style.display = "grid";
    div.style.gridTemplateColumns = "1fr ".repeat(7);
    div.style.border = "1px solid gray";
    div.style.margin = "0 0 .3em 0";
    for (let i = 0; i < ndays; i++) {
      const d = cr("div");
      let s = ["sun", "mon", "tue", "wed", "thu", "fri", "sat"][i % 7];
      if (monthmode && i < 7) {
        s += " week";
      }
      d.className = "calcell " + s;
      div.appendChild(d);
      d.onclick = () => {
        if (d.classList.contains("disabled")) {
          return;
        }
        const n = d.textContent;
        if (d.classList.contains("checked")) {
          d.classList.remove("checked");
          const day = new Day(this.getYear(), this.getMonth(), parseInt(n));
          this.checked = this.checked.filter((c) => !c.equals(day));
          this.changed();
        } else if (parseInt(n) == n) {
          if (this.getAttribute("single") == "true") {
            this.clear();
          }
          d.classList.add("checked");
          this.checked.push(
            new Day(this.getYear(), this.getMonth(), parseInt(n))
          );
          this.checked.sort(
            (a, b) => a.getDayOfGregorian() - b.getDayOfGregorian()
          );
          this.changed();
        }
      };
    }
    this.appendChild(div);

    style({
      ".calcell": {
        "font-size": "16px",
        "vertical-align": "middle",
        height: "2em",
        //"border": "1px solid gray",
        "line-height": "2em",
        margin: "0em 0",
        padding: "0em .5em",
        "text-align": "center",
        "background-color": "white",
      },
      ".checked": {
        "background-color": "#77e !important",
        color: "white",
      },
      ".disabled": {
        color: "#ccc",
      },
      ".sun": {
        "background-color": "#fee",
      },
      ".sat": {
        "background-color": "#eef",
      },
      ".holiday": {
        //"background-color": "#f88",
        color: "#f33",
      },
      ".disabled .holiday": {
        //"background-color": "#f88",
        color: "#fcc",
      },
    });

    this.redraw();
  }
  redraw() {
    if (this.onbeforedraw != null) {
      this.onbeforedraw();
    }
    this.yearmonth.textContent = this.day.year + "/" + this.day.month;
    const caloff = this.day.getFirstDayOfMonth().getWeek() % 7;
    const div = this.caldiv;
    const len = div.childNodes.length;
    if (this.monthmode) {
      for (let i = 0; i < 7; i++) {
        const d = div.childNodes[i];
        const s = ["sun", "mon", "tue", "wed", "thu", "fri", "sat"][i];
        d.innerHTML = s;
      }
      const lastday = this.day.getLastDayOfMonth().day;
      for (let i = 7; i < len; i++) {
        const d = div.childNodes[i];
        d.classList.remove("checked");
        d.classList.remove("disabled");
        let s = "";
        const day = i - 6 - caloff;
        if (day > 0 && day <= lastday) {
          const dday = new Day(this.day.year, this.day.month, day);
          const holiday = isHoliday(dday);
          //console.log(holiday, year, this.month, day);
          const cls = ["cellday"];
          if (holiday) {
            cls.push("holiday");
          }
          if (this.checked.find((d) => d.equals(dday))) {
            d.classList.add("checked");
          }
          if (this.available && !this.available.find((d) => d.equals(dday))) {
            d.classList.add("disabled");
          }
          s = "<div class='" + cls.join(" ") + "'>" + day + "</div>";
          if (this.ondraw != null) {
            const sc = this.ondraw(d, year, this.month, day);
            if (sc != null) {
              s = s + sc;
            }
          }
        }
        d.innerHTML = s;
      }
    } else {
      throw new Error("not supported monthmode false");
      /*
      for (let i = 0; i < len; i++) {
        const d = div.childNodes[i];
        let s = "";
        const day = i + 1 - caloff;
        const month = this.calcMonth(year, day);
        const day2 = this.calcMonthDay(year, day);
        d.style.background = month % 2 == 0 ? "#eee" : "#ddd";
        if (month > 0) {
          s = "<div class='cellday'>" + month + "/" + day2 + "</div>";
        }

        if (this.ondraw != null) {
          const sc = this.ondraw(year, day);
          if (sc != null) {
            s = s + sc;
          }
        }
        d.innerHTML = s;
      }
      */
    }
    this.changed();
  }
  changed() {
    if (this.onchange != null) {
      this.onchange();
    }
  }
  getYear() {
    return this.day.year;
  }
  getMonth() {
    return this.day.month;
  }
  getFirstDay() {
    return this.day.getFirstDayOfMonth();
  }
  getLastDay() {
    return this.day.getLastDayOfMonth();
  }
  setDay(day) {
    this.day = day;
    this.redraw();
  }
  prevMonth() {
    this.day = this.day.prevMonth();
    this.redraw();
  }
  nextMonth() {
    this.day = this.day.nextMonth();
    this.redraw();
  }
  thisMonth() {
    this.day = new Day();
    this.redraw();
  }
  makeDaysArray(days) {
    if (Array.isArray(days)) {
      return days
        .map((s) => {
          if (s instanceof Day) {
            return s;
          }
          try {
            return new Day(s);
          } catch (e) {}
          return null;
        })
        .filter((d) => d);
    }
    return days
      .split(",")
      .map((s) => {
        try {
          return new Day(s);
        } catch (e) {}
        return null;
      })
      .filter((d) => d);
  }
  get value() {
    return this.checked.map((d) =>`${d.toString()}(${["日", "月", "火", "水", "木", "金", "土"][d.getWeek()]})`).join(",");
  }
  set value(days) {
    this.checked = this.makeDaysArray(days);
    this.redraw();
  }
  clear() {
    this.caldiv.childNodes.forEach((d) => d.classList.remove("checked"));
    this.checked = [];
  }
  setAvailable(values) {
    this.available = this.makeDaysArray(values);
    this.redraw();
  }
}

customElements.define("input-dates", InputDates);

export { InputDates };
