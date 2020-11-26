//Imports
const fetch = require("isomorphic-unfetch")
const querystring = require("querystring")

//----------------- Request classes -----------------//
class CreateUserRequest {
  constructor(name, last_name, user_name) {
    this.name = name;
    this.last_name = last_name;
    this.user_name = user_name;
  }
}

class CreateGameRequest {
  constructor(user_id, rows, columns, mines) {
    this.user_id = user_id;
    this.rows = rows;
    this.columns = columns;
    this.mines = mines;
  }
}

class MakeAMoveRequest {
  constructor(user_id, type, row, column) {
    this.user_id = user_id;
    this.type = type;
    this.row = row;
    this.column = column;
  }
}

class ChangeGameStatusRequest {
  constructor(user_id) {
    this.user_id = user_id;
  }
}
//----------------- End of Request classes -----------------//


//----------------- RestClient -----------------//
class MinesweeperRestClient {
  constructor(config) {
    this.basePath = "https://nameless-ocean-97360.herokuapp.com/minesweeper";
  }
  request(endpoint = "", options = {}) {
    let url = this.basePath + endpoint;

    let headers = {
      "Content-type": "application/json"
    };

    let config = {
      ...options,
      ...headers
    };

    return fetch(url, config).then(r => {
      return r.json();
    });
  }

  createUser(createUserRequest) {
    let headers = {
      "Content-type": "application/json"
    };

    const options = {
      headers: headers,
      method: "POST",
      body: JSON.stringify(createUserRequest)
    };

    try {
      return this.request("/users", options);
    } catch(e) {
      return new Error(e);
    }
  }

  retrieveGamesByUserId(user_id) {
    let config = {
      method: "GET"
    };

    try {
      return this.request("/users/" + user_id + "/games", config);
    } catch(e) {
      return new Error(e);
    }
  }

  createGame(createGameRequest) {
    let headers = {
      "Content-type": "application/json"
    };

    const options = {
      headers: headers,
      method: "POST",
      body: JSON.stringify(createGameRequest)
    };

    try {
      return this.request("/games", options);
    } catch(e) {
      return new Error(e);
    }
  }

  makeAMove(game_id, makeAMoveRequest) {
    let headers = {
      "Content-type": "application/json"
    };

    const options = {
      headers: headers,
      method: "PATCH",
      body: JSON.stringify(makeAMoveRequest)
    };

    try {
      return this.request("/games/" + game_id, options);
    } catch(e) {
      return new Error(e);
    }
  }

  togglePlayOrPause(game_id, changeGameStatusRequest) {
    let headers = {
      "Content-type": "application/json"
    };

    const options = {
      headers: headers,
      method: "PATCH",
      body: JSON.stringify(changeGameStatusRequest)
    };

    try {
      return this.request("/games/" + game_id + "/status", options);
    } catch(e) {
      return new Error(e);
    }
  }
}
//----------------- End of RestClient -----------------//