import { invoke } from "@tauri-apps/api/core";
import { useEffect, useState } from "react";

type Loader = "vanilla" | "fabric";

type Profile = {
  id: string;
  name: string;
  minecraft_version: string;
  loader: Loader;
  loader_version: string | null;
};

export default function App() {
  const [profiles, setProfiles] = useState<Profile[]>([]);
  const [selected, setSelected] = useState<Profile | null>(null);
  const [accountName, setAccountName] = useState("");

  useEffect(() => {
    invoke<Profile[]>("list_profiles").then((items) => {
      setProfiles(items);
      setSelected(items[0] ?? null);
    });
  }, []);

  async function createProfile() {
    const profile = await invoke<Profile>("create_fabric_profile", {
      name: "Custom Fabric",
      minecraftVersion: "1.21.1"
    });
    setProfiles((items) => [...items, profile]);
  }

  async function createOfflineAccount() {
    await invoke("create_offline_account", { name: accountName });
  }

  return (
    <main className="app-shell">
      <aside>
        <h1>MT <span>CLIENT</span></h1>
        <nav>
          <button className="active">Play</button>
          <button>Profiles</button>
          <button>Mods</button>
          <button>Settings</button>
        </nav>
      </aside>

      <section className="content">
        <header>
          <div><small>INSTANCE</small><h2>{selected?.name ?? "No profile"}</h2></div>
          <button className="play" disabled={!selected}>PLAY</button>
        </header>

        <div className="profiles">
          {profiles.map((profile) => (
            <button
              key={profile.id}
              className={selected?.id === profile.id ? "profile selected" : "profile"}
              onClick={() => setSelected(profile)}
            >
              <strong>{profile.name}</strong>
              <span>{profile.loader} · Minecraft {profile.minecraft_version}</span>
            </button>
          ))}
        </div>

        <div className="actions">
          <button onClick={createProfile}>Create Fabric profile</button>
          <input
            value={accountName}
            onChange={(event) => setAccountName(event.target.value)}
            placeholder="Offline username"
          />
          <button onClick={createOfflineAccount}>Create offline account</button>
        </div>
      </section>
    </main>
  );
}
