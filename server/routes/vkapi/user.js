var axios = require('axios').default;

function makeRequest(reqName, access_token, params) {
	let paramsStr = [];
	for (var prop in params) {
		if (params.hasOwnProperty(prop)) {
			paramsStr.push(`${prop}=${params[prop]}`);
		}
	}
	return `https://api.vk.com/method/${reqName}?${paramsStr.join('&')}&access_token=${access_token}&v=5.101`;
}

async function getUserData(access_token, user_id) {
	var url = makeRequest('users.get', access_token, {
		"user_ids": user_id,
		"fields": "photo_100"
	});
	return await axios.get(url);
}

module.exports = {
	getUserData
};