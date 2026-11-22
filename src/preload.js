const { contextBridge, ipcRenderer } = require('electron');
contextBridge.exposeInMainWorld('mtClient', {
  openExternal: (url) => ipcRenderer.invoke('open-external', url)
});
