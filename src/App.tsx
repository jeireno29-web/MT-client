import { useState } from "react";

type Profile = {
  name: string;
  version: string;
  loader: "Fabric";
};

const profiles: Profile[] = [
  { name: "1.21.1 Fabric", version: "1.21.1", loader: "Fabric" },
  { name: "1.20.1 Fabric", version: "1.20.1", loader: "Fabric" },
  { name: "1.19.4 Fabric", version: "1.19.4", loader: "Fabric" }
];

export default function App() {
  const [selected, setSelected] = useState(profiles[0]);

  return (
    <main className="app-shell">
      <aside>
        <h1>MT <span>CLIENT</span></h1>
        <nav>
          <button className="active">Play</button>
          <button disabled>Profiles</button>
          <button disabled>Mods</button>
          <button disabled>Settings</button>
        </nav>
      </aside>

      <section className="content">
        <header>
          <div>
            <small>INSTANCE</small>
            <h2>{selected.name}</h2>
          </div>
          <button className="play" disabled>PLAY</button>
        </header>

        <div className="profiles">
          {profiles.map((profile) => (
            <button
              key={profile.name}
              className={selected.name === profile.name ? "profile selected" : "profile"}
              onClick={() => setSelected(profile)}
            >
              <strong>{profile.name}</strong>
              <span>{profile.loader} · Minecraft {profile.version}</span>
            </button>
          ))}
        </div>
      </section>
    </main>
  );
}
