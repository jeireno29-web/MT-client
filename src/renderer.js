const navs = document.querySelectorAll('.nav');
const pages = document.querySelectorAll('.page');

navs.forEach(button => button.addEventListener('click', () => {
  navs.forEach(item => item.classList.remove('active'));
  pages.forEach(page => page.classList.remove('active'));
  button.classList.add('active');
  document.getElementById(button.dataset.page).classList.add('active');
}));

document.getElementById('account').addEventListener('click', () => mtClient.openExternal('https://aka.ms/microsoft-account'));
document.getElementById('ram').addEventListener('input', event => document.getElementById('ramOutput').value = `${event.target.value} GB`);
document.getElementById('saveProfile').addEventListener('click', () => {
  const profile = { name: document.getElementById('profileName').value, ram: document.getElementById('ram').value };
  localStorage.setItem('mt-profile', JSON.stringify(profile));
  document.getElementById('status').textContent = `Perfil guardado: ${profile.name}`;
});
document.getElementById('playButton').addEventListener('click', () => document.getElementById('status').textContent = 'Motor de lanzamiento pendiente de configurar');

const saved = localStorage.getItem('mt-profile');
if (saved) {
  const profile = JSON.parse(saved);
  document.getElementById('profileName').value = profile.name;
  document.getElementById('ram').value = profile.ram;
  document.getElementById('ramOutput').value = `${profile.ram} GB`;
}
