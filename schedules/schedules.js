var Excel = require('exceljs');
var fs = require('fs');

var workbook = new Excel.Workbook();
workbook.xlsx.readFile('horarios_2017.1.xlsx')
    .then(function() {
        // use workbook
        let schedulesJSON = [];
        const daysOfWeek = {
          'DOM': 0,
          'SEG': 1,
          'TER': 2,
          'QUA': 3,
          'QUI': 4,
          'SEX': 5,
          'SAB': 6,
        };

        function getTime(d) {
          d = d.trim();

          let dayOfWeek = d.substr(0, 3);
          let timeStart = '';
          let timeEnd = '';
          
          let x = d.substr(4).split(':');
          if (x.length === 3) {
            let y = x[1].split(' ');
            if (y.length === 3) {
              timeStart = x[0] + ':' + y[0];
              timeEnd = y[2] + ':' + x[2];
            } else {
              timeStart = x[0].substr(x[0].length - 2, 2) + ':' + x[1].trim().substr(0, 2);
              timeEnd = x[1].substr(x[1].length - 2, 2) + ':' + x[2];
            }
          } else if (x.length === 2) {
            timeStart = x[0] + ':' + x[1].substr(0, 2);
            timeEnd = x[1].substr(x[1].length - 4, 4);
          }

          if (timeStart.length >= 5)
            timeStart = timeStart.substr(timeStart.length - 5, 5);

          return {
            dayOfWeek: daysOfWeek[dayOfWeek],
            timeStart: timeStart,
            timeEnd: timeEnd
          };
        }

        for (let index = 4; index <= 1488; index++) {
          let times = [];

          let d = workbook.worksheets[0].getCell('D' + index).value;
          if (d && d.length > 3) {
            times.push(getTime(d));
          }

          let e = workbook.worksheets[0].getCell('E' + index).value;
          if (e && e.length > 3) {
            times.push(getTime(e));
          }

          let f = workbook.worksheets[0].getCell('F' + index).value;
          if (f && f.length > 3) {
            times.push(getTime(f));
          }
          
          let schedule = {
            class: "" + workbook.worksheets[0].getCell('C' + index).value,
            cod: workbook.worksheets[0].getCell('A' + index).value ? "" + workbook.worksheets[0].getCell('A' + index).value : '',
            departament: workbook.worksheets[0].getCell('J' + index).value ? "" + workbook.worksheets[0].getCell('J' + index).value : '',
            name: "" + workbook.worksheets[0].getCell('B' + index).value,
            period: "" + workbook.worksheets[0].getCell('H' + index).value,
            place: workbook.worksheets[0].getCell('K' + index).value ? "" + workbook.worksheets[0].getCell('K' + index).value : '',
            schedules: times,
            type: '',
            vacancies: ''
          };

          schedulesJSON.push(schedule);

        }

        fs.writeFile('schedules.json', JSON.stringify(schedulesJSON, null, 4), 'utf8', null);
    });