var axios = require('axios');
var express = require('express');
var router = express.Router();
var dbinfo = require('./dbinfo');
var DBClinet = require('mongodb').MongoClient;
var ObjectId = require('mongodb').ObjectId;

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
					.insertOne({ "site_token": token, "money": 1000 }, function(err, data) {
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

router.post('/add-money', function (req, res, next) {
	const count = Number(req.query.count);
	const id = req.query.user_id;
	const sendOk = function() {
		res.send({status: "ok"});
	};
	const oid = new ObjectId(id);
	DBClinet.connect(dbinfo.connStr, function(err, userClient) {
		userClient
			.db(dbinfo.db)
			.collection(dbinfo.usersCollection)
			.findOne({"_id": oid}, function (err,data) {
				if (err) {
					userClient.close();
					res.sendStatus = 400;
					res.send({status: "error"});
				} else {
					const newMoney = data.money + count;
					const newvalues = { $set: {money: newMoney}};
					userClient
						.db(dbinfo.db)
						.collection(dbinfo.usersCollection)
						.updateOne({"_id": oid}, newvalues, function (err, res) {
							if (err) {
								userClient.close();
								res.sendStatus = 400;
								res.send({status: "error"});
							} else {
								userClient.close();
								res.sendStatus = 200;
								sendOk();
							}
						})
				}
			});
	});
});

router.get('/info', function (req, res, next) {
	const id = req.query.user_id;
	const oid = new ObjectId(id);
	const sendOk = function() {
		res.send({status: "ok"});
	};
	const user = {
		name: '',
		avatar_url: '',
		money: '',
		challenge_finished: '',
		challenge_created: '',
		challenge_now: ''
	};
	DBClinet.connect(dbinfo.connStr, function(err, userClient) {
		userClient
			.db(dbinfo.db)
			.collection(dbinfo.usersCollection)
			.findOne({"_id": oid}, function (err,data) {
				if (err) {
					userClient.close();
					res.sendStatus = 400;
					res.send({status: "error"});
				} else {
					user.name = data.name || 'not present';
					user.avatar_url = data.avatar_url || 'not present';
					user.money = data.money || 'not present';
					res.send({user: user});
				}
			})
	})
});

module.exports = router;
