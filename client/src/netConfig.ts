import axios from 'axios'

axios.defaults.baseURL = "http://localhost:9999"
axios.defaults.timeout = 5000


export const auth = {
  login(data){
    return axios.post('/UserAccount/login', data)
  },
  signup(data){
    return axios.post('/UserAccount/signup', data)
  },
  update(data){
    return axios.post('/UserAccount/update', data)
  }
}
export const user = {
  query(data){
    return axios.post('/UserAccount/find', data)
  },
  delete(data){
    return axios.post('/UserAccount/delete', data)
  }
}
export const resume = {
  query(data){
    return axios.post('/NormalUser/findOne', data)
  },
  update(data){
    return axios.post('/NormalUser/update', data)
  }
}

export const message = {
  query(data){
    return axios.post('/Information/find', data)
  },
  update(data){
    return axios.post('/Information/update', data)
  }
}

export const news = {
  query(data){
    return axios.post('/News/find', data)
  },
  delete(data){
    return axios.post('/News/delete', data)
  },
  insert(data){
    return axios.post('/News/insert', data)
  }
}

export const job = {
  query(data){
    return axios.post('/JobPosition/find', data)
  },
  delete(data) {
    return axios.post('/JobPosition/delete', data)
  },
  insert(data){
    return axios.post('/JobPosition/insert', data)
  },
  update(data){
    return axios.post('/JobPosition/update', data)
  }
}

export const application = {
  query(data){
    return axios.post('/ApplicationInfo/find', data)
  },
  insert(data){
    return axios.post('/ApplicationInfo/insert', data)
  },
  updateOut(data){
    return axios.post('/ApplicationInfo/updateOut', data)
  },
  updateIn(data){
    return axios.post('/ApplicationInfo/updateIn', data)
  },
  statistics(data){
    return axios.post('/ApplicationInfo/stats', data)
  }
}