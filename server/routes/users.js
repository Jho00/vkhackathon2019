var axios = require('axios');
var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.get('/auth', function(req, res, next) {
  let code = req.query.code;

  axios.get("https://oauth.vk.com/access_token", {
      params: {
        code: code,
        redirect_uri: "http://127.0.0.1:8080/redirect",
        client_secret: "UvqccxvCevnGOxsjoqFy",
        client_id: "7150584"
      }
  }).then(json => console.log(json)).catch(json => console.log(json));
  res.send("huy");
});

module.exports = router;
