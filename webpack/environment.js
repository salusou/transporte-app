module.exports = {
  __TIMESTAMP__: String(new Date().getTime()),
  __VERSION__: process.env.hasOwnProperty('APP_VERSION') ? process.env.APP_VERSION : '1.0.0',
  __DEBUG_INFO_ENABLED__: false,
  __SERVER_API_URL__: '',
};
