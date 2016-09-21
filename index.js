const express = require('express');
const request = require('request');

const bodyParser = require('body-parser');

let app = express();

app.use( bodyParser.json() );       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
	extended: true
})); 

app.post('/', (req, res) => {
	request.post('http://ava.ufrpe.br/login/token.php', {
		form: {
			'username': req.body.username,
			'password': req.body.password,
			'service': 'moodle_mobile_app'
		}
	}, function (error, response, body) {
		if (!error && response.statusCode == 200) {
			let token = JSON.parse(body).token;

			request.post('http://ava.ufrpe.br/webservice/rest/server.php?moodlewsrestformat=json', {
				form: {
					'moodlewssettingfilter': true,
					'moodlewssettingfileurl': true,
					'wsfunction': 'core_webservice_get_site_info',
					'wstoken': token
				}
			}, (error, response, body) => {
				res.send(JSON.parse(body));
			})
		}
	})
});

app.listen(8088);

