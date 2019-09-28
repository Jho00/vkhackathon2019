var axios = require('axios');
var express = require('express');
var router = express.Router();
var dbinfo = require('./dbinfo');
var getUserData = require('./vkapi/user').getUserData;
var DBClinet = require('mongodb').MongoClient;

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

		console.log("----------");
		console.log(json.data);
		console.log("----------");

		let token = json.data.access_token;
		let user_id = json.data.user_id.toString();
		let done = false;

		var db = new DBClinet(dbinfo.connStr, { useNewUrlParser: true, useUnifiedTopology: true });

		db.connect(function(err, userClient) {
			if (err) {
				userClient.close();
				res.send({ status: "error" });
			}

			userClient
				.db(dbinfo.db)
				.collection(dbinfo.usersCollection)
				.findOne({ "_id": user_id }, function (error, data) {
					
					if (error) {
						res.send({ status: "error" });
						userClient.close();
					} else if (data) {
						res.send({ status: "success", data: data._id });
						userClient.close();
					} else {
						userClient
							.db(dbinfo.db)
							.collection(dbinfo.usersCollection)
							.insertOne({
									"_id": user_id,
									"site_token": token,
									"money": 1000
								}, function(err, insertResult) {
								if (err) {
									res.send({ status: "error" });
									userClient.close();
								}

								res.send({ status: "success", data: user_id });
								userClient.close();
							})
					}
				});
		});
	})
		.catch(err => {
			res.send({
				status: "error"
			});
		});
});

router.post('/add-money', function (req, res, next) {
	const count = Number(req.body.count);
	const id = req.body.user_id;
	const sendOk = function() {
		res.send({status: "ok"});
	};
	DBClinet.connect(dbinfo.connStr, function(err, userClient) {
		userClient
			.db(dbinfo.db)
			.collection(dbinfo.usersCollection)
			.findOne({"_id": id}, function (err,data) {
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
						.updateOne({"_id": id}, newvalues, function (err, res) {
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

/* GET users listing. */
router.get('/info', function (req, res, next) {
	let user_id = req.query.user_id;

	DBClinet.connect(
			dbinfo.connStr, {
				useNewUrlParser: true,
				useUnifiedTopology: true
			}, function (err, client) {

			if (err) {
				res.send({status: "error"});
			}

			client
				.db(dbinfo.db)
				.collection(dbinfo.usersCollection)
				.findOne({"_id": user_id}, function (error, result) {


					if (error) {
						res.send({status: "error"});
						client.close();

						return;
					}

					if (!result) {
						res.send({ status: "error" });
						client.close();
						return;
					}

					const { site_token, money } = result;

					getUserData(site_token, user_id).then(usrdb => {

						client
							.db(dbinfo.db)
							.collection(dbinfo.challengesCollection)
							.find({})
							.toArray(function (er, challenges) {
								console.log(challenges);
								res.send({
									status: "success",
									data: {
										...usrdb.data.response[0],
										money,
										"ownChallengesCount":
											challenges.filter(el => 
												el.author_id == user_id).length,
										"acceptChallengesCount":
											challenges
												.filter(el =>
													el.status == "active")
												.filter(el =>
													el.users
													.map(usr => usr._id)
													.indexOf(user_id) != -1).length,
										"passedChallengesCount":
											challenges
												.filter(el =>
													el.status == "passed")
												.filter(el =>
													el.users
													.map(usr => usr._id)
													.indexOf(user_id) != -1).length,
								}});
								client.close();
							});
					})
				})
	})

	getUserData()
});

module.exports = router;
