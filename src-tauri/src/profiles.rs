#[derive(Clone, Debug, serde::Serialize, serde::Deserialize)]
pub struct Profile {
    pub id: String,
    pub name: String,
    pub minecraft_version: String,
    pub loader: Loader,
    pub loader_version: Option<String>,
}

#[derive(Clone, Debug, serde::Serialize, serde::Deserialize)]
#[serde(rename_all = "lowercase")]
pub enum Loader {
    Vanilla,
    Fabric,
}

impl Profile {
    pub fn fabric(name: &str, minecraft_version: &str) -> Self {
        Self {
            id: uuid::Uuid::new_v4().to_string(),
            name: name.to_string(),
            minecraft_version: minecraft_version.to_string(),
            loader: Loader::Fabric,
            loader_version: None,
        }
    }
}
