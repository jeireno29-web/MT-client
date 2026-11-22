const STORAGE_KEY = "mt-client-settings";

const defaultProfiles = [
  { name: "Principal", version: "1.21.1", loader: "Vanilla" },
  { name: "Modded", version: "1.20.1", loader: "Fabric" }
];

const state = loadState();

const versionSelect = document.querySelector("#versionSelect");
const loaderSelect = document.querySelector("#loaderSelect");
const heroVersion = document.querySelector("#heroVersion");
const heroLoader = document.querySelector("#heroLoader");
const playMeta = document.querySelector("#playMeta");
const playButton = document.querySelector("#playButton");
const notice = document.querySelector("#notice");

function loadState() {
  try {
    const saved = JSON.parse(localStorage.getItem(STORAGE_KEY));
    return { profiles: defaultProfiles, ...saved };
  } catch {
    return { profiles: defaultProfiles };
  }
}

function saveState() {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(state));
}

function updateSelection() {
  const version = versionSelect.value;
  const loader = loaderSelect.value;
  heroVersion.textContent = `Minecraft ${version}`;
  heroLoader.textContent = `${loader} · Perfil principal`;
  playMeta.textContent = `${version} · ${loader}`;
  state.selected = { version, loader };
  saveState();
}

function showNotice(message) {
  notice.textContent = message;
  notice.hidden = false;
  clearTimeout(showNotice.timer);
  showNotice.timer = setTimeout(() => { notice.hidden = true; }, 6000);
}

function renderProfiles() {
  const list = document.querySelector("#profileList");
  list.replaceChildren();

  state.profiles.forEach((profile, index) => {
    const card = document.createElement("article");
    card.className = "profile-card";

    const title = document.createElement("strong");
    title.textContent = profile.name;
    const info = document.createElement("small");
    info.textContent = `${profile.version} · ${profile.loader}`;
    const button = document.createElement("button");
    button.type = "button";
    button.textContent = "Usar perfil";
    button.addEventListener("click", () => {
      versionSelect.value = profile.version;
      loaderSelect.value = profile.loader;
      updateSelection();
      switchView("play");
    });

    card.append(title, info, button);
    list.append(card);
  });
}

function switchView(view) {
  document.querySelectorAll(".nav-item").forEach(button => {
    const active = button.dataset.view === view;
    button.classList.toggle("active", active);
    button.toggleAttribute("aria-current", active);
  });

  document.querySelectorAll(".view").forEach(panel => panel.classList.remove("active"));
  document.querySelector(`#${view}View`).classList.add("active");

  const titles = { play: "Jugar", profiles: "Perfiles", settings: "Ajustes" };
  document.querySelector("#pageTitle").textContent = titles[view];
}

function createProfile() {
  const name = window.prompt("Nombre del perfil", `Perfil ${state.profiles.length + 1}`);
  if (!name?.trim()) return;

  state.profiles.push({
    name: name.trim(),
    version: versionSelect.value,
    loader: loaderSelect.value
  });
  saveState();
  renderProfiles();
  showNotice(`Perfil “${name.trim()}” creado.`);
}

document.querySelectorAll(".nav-item").forEach(button => {
  button.addEventListener("click", () => switchView(button.dataset.view));
});

document.querySelector("#newProfileButton").addEventListener("click", createProfile);
document.querySelector("#refreshButton").addEventListener("click", () => showNotice("Instancias verificadas y listas."));

versionSelect.addEventListener("change", updateSelection);
loaderSelect.addEventListener("change", updateSelection);

playButton.addEventListener("click", () => {
  playButton.classList.add("loading");
  playButton.querySelector("span").textContent = "INICIANDO";

  setTimeout(() => {
    playButton.classList.remove("loading");
    playButton.querySelector("span").textContent = "JUGAR";
    showNotice("MVP preparado. En la versión web no se puede iniciar el proceso local de Minecraft.");
  }, 900);
});

const ramInput = document.querySelector("#ramInput");
const ramOutput = document.querySelector("#ramOutput");
ramInput.addEventListener("input", () => {
  ramOutput.textContent = `${ramInput.value} GB`;
  state.ram = Number(ramInput.value);
  saveState();
});

if (state.selected) {
  versionSelect.value = state.selected.version;
  loaderSelect.value = state.selected.loader;
}
if (state.ram) {
  ramInput.value = state.ram;
  ramOutput.textContent = `${ramInput.value} GB`;
}

renderProfiles();
updateSelection();
