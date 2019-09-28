var axios = require('axios');
var express = require('express');
var router = express.Router();
var dbinfo = require('./dbinfo');
var usersdb = require('../db/users');
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

	dbclient.connect(function(err, client) {
		if (err) {
			console.log(err);
			res.send({ status: "error"});
		} else {

			client
				.db(dbinfo.db)
				.collection(dbinfo.challengesCollection)
				.find({})
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

	let id = req.query.user_id;

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

			usrCl.findOne({ "_id": new ObjectID(id) }, function (err, data) {
				if (err) {
					console.log(err);
					res.send({ status: "error"});
				} else {
					console.log("+++++++1");
					console.log(data);
					console.log("+++++++");
					chgCl.insertOne({
						...req.body,
						money_pull: req.body.num * req.body.cost,
						users: [ data ]
					}, function(error, data) {
						if (error) {
							console.log(error);
							res.send({ status: "error"});
						}
						console.log(data);
						res.send({status: "success", data: data.insertedId});
						client.close();
					});
				}
			});
		}
	});
})

module.exports = router;
