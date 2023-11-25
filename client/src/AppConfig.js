export const AppConfig = {
  apiUrl: process.env.REACT_APP_API_URL,
  routerBase: "",
};

export const ImageConfig = {
  apiUrl: process.env.IMAGE_API_URL
    ? process.env.IMAGE_API_URL
    : "http://localhost:80/article/",
  routerBase: process.env.IMAGE_ROUTER_BASE || "",
};

export function getImageUrl(id) {
  return `${ImageConfig.apiUrl}${id}.png`;
}

// export const connectIdentity = process.env.REACT_APP_CONNECT_IDENTITY;