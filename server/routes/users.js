var axios = require('axios');
var express = require('express');
var router = express.Router();
var dbinfo = require('./dbinfo');
var DBClinet = require('mongodb').MongoClient;

/* GET users listing. */
router.get('/', function (req, res, next) {
	res.send('respond with a resource');
});

router.get('/auth', function (req, res, next) {
	let code = req.query.code;

	axios.get("https://oauth.vk.com/access_token", {
		params: {
			code: code,
			redirect_uri: "http://127.0.0.1:8080/redirect",
			client_secret: "UvqccxvCevnGOxsjoqFy",
			client_id: "7150584"
		}
	}).then(json => {

		let token = json.data.access_token;
		let done = false;

		DBClinet.connect(dbinfo.connStr, function(err, userClient) {
			userClient
				.db(dbinfo.db)
				.collection(dbinfo.usersCollection)
				.find({ "site_token": token })
				.toArray(function (err, data) {
					if (err) {
						console.log(err);
						res.sendStatus = 400;
						res.send({ status: "error" });
					} else if (data.length > 0) {
						res.send({ status: "success", data: data[0]._id })
						done = true;
					}
				});
			
			if (!done) {
				userClient
					.db(dbinfo.db)
					.collection(dbinfo.usersCollection)
					.insertOne({ "site_token": token, "money": 0 }, function(err, data) {
						if (err) {
							console.log(err);
							res.sendStatus = 400;
							res.send({ status: "error" });
						}

						console.log(data.insertedId);
						res.send({ status: "success", data: data.insertedId });
					})
				
			}

			userClient.close();
		});
	})
		.catch(err => {
			console.log(err);
			res.send({
				status: "error"
			});
		});
});

module.exports = router;
