var axios = require('axios');
var express = require('express');
var router = express.Router();
var dbinfo = require('./dbinfo');
// var usersdb = require('../db/users');
var DBClient = require('mongodb').MongoClient;
var ObjectID = require('mongodb').ObjectID;

const dbclient = new DBClient(dbinfo.connStr, { useNewUrlParser: true, useUnifiedTopology: true });

function clone(obj) {
    if (null == obj || "object" != typeof obj) return obj;
    var copy = obj.constructor();
    for (var attr in obj) {
        if (obj.hasOwnProperty(attr)) copy[attr] = obj[attr];
    }
    return copy;
}

/* GET users listing. */
router.get('/', function (req, res, next) {
	let id = req.query.challenge_id;
	let cond = { status: "passive" };
	if (id) {
		cond = {'_id' : new ObjectID(id)};
	}
	dbclient.connect(function(err, client) {
		if (err) {
			console.log(err);
			res.send({ status: "error"});
		} else {

			client
				.db(dbinfo.db)
				.collection(dbinfo.challengesCollection)
				.find(cond)
				.toArray(function(error, data) {
					if (error) {
						console.log(error);
						res.send({ status: "error"});
					}
					console.log(data);
					res.send({status: "success", data: data});
			});

			client.close();
		}
	});
});

router.post('/', function (req, res) {

	let id = req.body.user_id;

	dbclient.connect(function(err, client) {
		if (err) {
			console.log(err);
			res.send({ status: "error"});
		} else {

			let chgCl = client
				.db(dbinfo.db)
				.collection(dbinfo.challengesCollection);
			let usrCl = client
				.db(dbinfo.db)
				.collection(dbinfo.usersCollection);

			usrCl.findOne({ "_id": id }, function (err, data) {
				if (err) {
					res.send({ status: "error"});
				} else {

					let now = new Date(Date.now());
					let expDate = new Date(now.getTime() + 1000 * 60 * 60 * 24 * req.body.days);

					chgCl.insertOne({
						...req.body,
						money_pull: req.body.num * req.body.cost,
						users: [ data ],
						author_id: id,
						status: "passive",
						created_at: now,
						expires_at: expDate
					}, function(error, data) {
						if (error) {
							res.send({ status: "error"});
						}

						res.send({status: "success", data: data.insertedId});
						client.close();
					});
				}
			});
		}
	});
});

router.get('/join', function (req, res) {
	let id = req.query.user_id;
	let challenge_id = req.query.challenge_id;

	dbclient.connect(function(err, client) {
		if (err) {
			console.log(err);
			res.send({ status: "error"});
			dbclient.close();
			return;
		}
		let chgCl = client
			.db(dbinfo.db)
			.collection(dbinfo.challengesCollection);
		let usrCl = client
			.db(dbinfo.db)
			.collection(dbinfo.usersCollection);

		usrCl.findOne({ "_id": id }, function (err, userData) {
			if (err || !userData) {
				console.log(err);
				res.send({ status: "error"});
				dbclient.close();
			} else {
				chgCl.findOne({'_id': new ObjectID(challenge_id)}, function (error, challengeData) {
					if (error || !challengeData) {
						console.log(error);
						res.send({ status: "error"});
						dbclient.close();
					} else {
						if (userData.money < challengeData.cost) {
							res.send({ status: "error"});
							dbclient.close();
						}

						usrCl.updateOne(
							{'_id': id },
							{ $set: { "money": userData.money - challengeData.cost} },
							function (usrErr, usrData) {
								if (usrErr) {
									res.send({ status: "error"});
									dbclient.close();
								}

								let updateCondition = {
									$push: {
										'users': userData
									}
								}

								if (challengeData.num >= challengeData.users.length - 1) {
									updateCondition = {
										...updateCondition,
										$set: {
											status: "active"
										}
									}
								}

								chgCl.updateOne(
									{'_id': new ObjectID(challenge_id)},
									updateCondition,
									function (chgErr, data) {
										if (chgErr) {
											console.log(chgErr);
											res.send({ status: "error"});
											dbclient.close();
										} else {
											res.send({ status: "success"});
											dbclient.close();
										}
									})
							})
					}
				});
			}
		})
	})
});

router.get('/my', function(req, res) {
	let user_id = req.query.user_id;

	dbclient.connect(function(connErr, client) {
		if (connErr) {
			console.log(connErr);
			res.send({ status: "error" });
			dbclient.close();
		}

		client
			.db(dbinfo.db)
			.collection(dbinfo.challengesCollection)
			.find({ author_id: user_id })
			.toArray(function(chgErr, myChgs) {
				if (chgErr) {
					console.log(chgErr);
					res.send({ status: "error" });
					dbclient.close();
				}

				res.send({ status: "success", data: myChgs });
				dbclient.close();
			});
	})
});

router.get('/accept', function (req, res) {
	let user_id = req.query.user_id;

	dbclient.connect(function(connErr, client) {
		if (connErr) {
			console.log(connErr);
			res.send({ status: "error" });
			dbclient.close();
		}

		client
			.db(dbinfo.db)
			.collection(dbinfo.challengesCollection)
			.find({
				users: {
					$elemMatch: {
						"_id": user_id
					}
				}
			})
			.toArray(function (chgErr, accChgs) {
				if (chgErr) {
					console.log(chgErr);
					res.send({ status: "error" });
					dbclient.close();
				}

				res.send({ status: "success", data: accChgs });
				dbclient.close();
			})
	})
})

router.post('/vote', function (req, res) {
	let user_id = req.body.user_id;
	let challenge_id = req.body.challenge_id;
	let votedUsers = req.body.votes;

	dbclient.connect(function(connErr, client) {
		if (connErr) {
			console.log(connErr);
			res.send({ status: "error" });
			dbclient.close();
		} else {

			client
				.db(dbinfo.db)
				.collection(dbinfo.challengesCollection)
				.findOne(
					{ "_id": new ObjectID(challenge_id) },
					function (chgErr, challenge) {
						if (chgErr) {
							console.log(chgErr);
							res.send({ status: "error" });
							dbclient.close();
						} else {

							let chgUsers = challenge.users;
							let currUsr = chgUsers.find(usr => usr._id == user_id);

							votedUsers = currUsr.voted
								? chgUsers
								: votedUsers.map(votedUser => {
									let usr = chgUsers.find(usr => usr._id == votedUser._id);
									
									if (!usr.votes) {
										usr.votes = 0;
									}
									if (votedUser.voted) {
										usr.votes++;
									}
									if (usr._id == user_id) {
										usr.voted = true;
									}

									return usr;
								});

							client
								.db(dbinfo.db)
								.collection(dbinfo.challengesCollection)
								.updateOne(
									{ "_id": new ObjectID(challenge_id) },
									{ $set: { users: votedUsers }
								}, function (updErr, data) {
									if (updErr) {
										console.log(updErr);
										res.send({ status: "error" });
										dbclient.close();
									} else {
										res.send({ status: "success" });
										dbclient.close();
									}
								});
						}
					});
		}
	})
});

module.exports = router;
